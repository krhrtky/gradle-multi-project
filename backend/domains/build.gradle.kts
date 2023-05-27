import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(libs.spring.autoconfigure)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.spring.starter.test) {
        exclude("org.junit.vintage:junit-vintage-engine")
    }
    testImplementation(kotlin("test"))
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
