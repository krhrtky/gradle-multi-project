plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    id("org.jetbrains.kotlin.plugin.spring") version libs.versions.kotlin
}

dependencies {
    implementation(project(":backend:domains"))
    implementation(libs.spring.autoconfigure)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.spring.starter.test) {
        exclude("org.junit.vintage:junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation(kotlin("test"))
}

tasks.bootJar {
    enabled = false
}
