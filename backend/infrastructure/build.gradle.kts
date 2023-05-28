import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.Logging
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    id("nu.studer.jooq") version "6.0.1"
}

dependencies {
    implementation(libs.spring.autoconfigure)
    implementation(libs.spring.starter.jooq)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(project(":backend:domains"))
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    testImplementation(libs.spring.starter.test) {
        exclude("org.junit.vintage:junit-vintage-engine")
    }
    testImplementation(kotlin("test"))

    compileOnly("org.jooq:jooq-codegen")
    compileOnly("org.jooq:jooq")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
    jooqGenerator("com.mysql:mysql-connector-j:8.0.33")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

val x = rootProject.dependencyManagement.importedProperties["jooq.version"]

buildscript {
    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group == "org.jooq") {
            useVersion("3.14.3")
        }
    }
}

jooq {
    version.set(x)
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = "jdbc:mysql://localhost:3306/app"
                    user = "root"
                    password = "password"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "app"
                    }
                    generate.apply {
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.example.infrastructure.db"
                        directory ="${projectDir}/build/jooq/main"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}
