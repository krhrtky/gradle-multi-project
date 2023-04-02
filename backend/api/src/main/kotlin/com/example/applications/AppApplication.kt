package com.example.applications

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.example"])
open class AppApplication

fun main(args: Array<String>) {
  runApplication<AppApplication>(*args)
}
