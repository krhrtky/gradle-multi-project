package com.example.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AppApplication

fun main(args: Array<String>){
  runApplication<AppApplication>(*args)
}
