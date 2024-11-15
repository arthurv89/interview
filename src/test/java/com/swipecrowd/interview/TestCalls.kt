package com.swipecrowd.interview

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.ResponseEntity

class TestCalls(
    val host: String,
    val restTemplate: TestRestTemplate
) {
    inline fun <reified T: Any> doGet(path: String): ResponseEntity<T>? =
        restTemplate.getForEntity("$host$path")

    inline fun <reified T: Any> doPost(path: String, body: Any): ResponseEntity<T>? =
        restTemplate.postForEntity("$host$path", body)
}