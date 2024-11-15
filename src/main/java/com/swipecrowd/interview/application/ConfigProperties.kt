package com.swipecrowd.interview.application

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@ConfigurationProperties
class ConfigProperties {
    private val hostName: String? = null
    private val port = 0

    @Bean fun restTemplate() = RestTemplate()
}