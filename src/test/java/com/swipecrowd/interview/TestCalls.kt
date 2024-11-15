package com.swipecrowd.interview

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.test.web.client.postForObject

class TestCalls(
    val host: String,
    val restTemplate: TestRestTemplate
) {
    inline fun <reified T: Any> doGet(path: String): T? = restTemplate.getForObject("$host$path")
    inline fun <reified T: Any> doPost(path: String, body: Any): T? = restTemplate.postForObject("$host$path", body)
}