import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.spring") version libs.versions.kotlin
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.detekt)
}

detekt {
    val targetDir = listOf("backend").flatMap {
        listOf(
            "$it/src/main/kotlin",
            "$it/src/test/kotlin",
        )
    }
    buildUponDefaultConfig = true
    allRules = false
    source = files(targetDir)
    config = files("$rootDir/config/detekt/detekt.yml")
    baseline = file("$rootDir/config/detekt/baseline.xml")
    basePath = projectDir.path
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
        sarif {
            required.set(true)
            outputLocation.set(file("/tmp/report/detekt.sarif"))
        }
        md.required.set(true) // simple Markdown format
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}

allprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "java")
    apply(plugin = "kotlin")
    group = "org.example"
    version = "1.0-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_17
}

subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.withType<BootRun> {
    dependsOn("generateJooq")
}
