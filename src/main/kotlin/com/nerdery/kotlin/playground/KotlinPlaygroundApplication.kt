package com.nerdery.kotlin.playground

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KotlinPlaygroundApplication

fun main(args: Array<String>) {
    SpringApplication.run(KotlinPlaygroundApplication::class.java, *args)
}
