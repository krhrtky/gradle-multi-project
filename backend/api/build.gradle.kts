plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    id("com.netflix.dgs.codegen") version "5.11.1"
}

dependencies {
    implementation(libs.spring.starter.web)
    implementation(libs.spring.starter.actuator)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(platform(libs.graphql.dgs.platform))
    implementation(libs.graphql.dgs.spring.boot)
    implementation(project(":backend:domains"))
    implementation(project(":backend:infrastructure"))
    testImplementation(libs.spring.starter.test) {
        exclude("org.junit.vintage:junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation(kotlin("test"))
}

tasks.bootJar {
    archiveFileName.set("api.jar")
}

tasks.generateJava {
    schemaPaths = mutableListOf("${rootProject.projectDir}/schema.graphql")
    packageName = "com.example.applications.graphql"
}

tasks.build {
    dependsOn("generateJava")
}
