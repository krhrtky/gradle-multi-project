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
    val targetDir = project.allprojects.flatMap {
        listOf(
            "${it.projectDir}/src/main/kotlin",
            "${it.projectDir}/src/test/kotlin",
        )
    }
    buildUponDefaultConfig = true
    allRules = false
    source.from(files(targetDir))
    config.from(files("$rootDir/config/detekt/detekt.yml"))
    baseline = file("$rootDir/config/detekt/baseline.xml")
    basePath = projectDir.path
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(false)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(true)
        md.required.set(false) // simple Markdown format
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
