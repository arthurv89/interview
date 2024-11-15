package com.swipecrowd.interview.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.client.RestTemplate

@Configuration
@ConfigurationProperties
class ConfigProperties {
    private val hostName: String? = null
    private val port = 0

    @Bean fun restTemplate() = RestTemplate()


    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = Jackson2ObjectMapperBuilder.json().build<ObjectMapper>()
        objectMapper.registerModule(JavaTimeModule())
        return objectMapper
    }
}