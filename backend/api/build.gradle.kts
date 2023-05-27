import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(libs.spring.starter.web)
    implementation(libs.spring.starter.actuator)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(project(":backend:domains"))
    implementation(project(":backend:infrastructure"))
    testImplementation(libs.spring.starter.test) {
        exclude("org.junit.vintage:junit-vintage-engine")
    }
    testImplementation(kotlin("test"))
}

tasks.withType<BootRun> {
    jvmArgs = listOf("--illegal-access=deny")
}
