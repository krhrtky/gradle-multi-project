import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    autoCorrect = true
    parallel = true
    buildUponDefaultConfig = true
    allRules = false
    source.from(files(targetDir))
    config.from(files("$rootDir/config/detekt/detekt.yml"))
    baseline = file("$rootDir/config/detekt/baseline.xml")
    basePath = projectDir.path
}
dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.0")
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
    java.sourceCompatibility = JavaVersion.VERSION_17
}

subprojects {
    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
        testImplementation("io.kotest:kotest-assertions-core:5.6.2")
        testImplementation("io.kotest:kotest-property:5.6.2")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
        testImplementation("io.mockk:mockk:1.13.5")
    }

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
