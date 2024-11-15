package com.swipecrowd.interview.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.swipecrowd.interview"])
@EnableConfigurationProperties(ConfigProperties::class)
class ExpensesApplication

fun main(args: Array<String>) {
    runApplication<ExpensesApplication>(*args)
}