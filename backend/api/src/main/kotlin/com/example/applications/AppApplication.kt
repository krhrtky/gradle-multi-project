package com.example.applications

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = ["com.example"])
@ConfigurationPropertiesScan(basePackages = ["com.example"])
@ComponentScan(basePackages = ["com.example"])
open class AppApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}
