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
    testImplementation(libs.spring.starter.test) {
        exclude("org.junit.vintage:junit-vintage-engine")
    }
    testImplementation(kotlin("test"))
}
