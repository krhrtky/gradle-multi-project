import org.jetbrains.kotlin.commonizer.OptimisticNumberCommonizationEnabledKey.alias
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.spring") version libs.versions.kotlin
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

allprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "java")
    apply(plugin = "kotlin")
    group = "org.example"
    version = "1.0-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11
}

subprojects {

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
