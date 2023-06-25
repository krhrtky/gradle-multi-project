import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.Logging

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    id("org.jetbrains.kotlin.plugin.spring") version libs.versions.kotlin
    id("nu.studer.jooq") version "8.2.1"
}

dependencies {
    implementation(libs.spring.autoconfigure)
    implementation(libs.spring.starter.jooq)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(project(":backend:domains"))

    testImplementation(libs.spring.starter.test) {
        exclude("org.junit.vintage:junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation(kotlin("test"))

    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
    jooqGenerator("com.mysql:mysql-connector-j:8.0.33")
}

val jooqVersion = rootProject.dependencyManagement.importedProperties["jooq.version"]

jooq {
    version.set(jooqVersion)
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {

            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = "jdbc:mysql://localhost:3306/app"
                    user = "root"
                    password = "password"
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "app"
                    }
                    generate.apply {
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                        isPojosAsKotlinDataClasses = true
                        isKotlinNotNullRecordAttributes = true
                    }
                    target.apply {
                        packageName = "com.example.infrastructure.db"
                        directory = "$projectDir/build/jooq/main"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks.bootJar {
    enabled = false
}
