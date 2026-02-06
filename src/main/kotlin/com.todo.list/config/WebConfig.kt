package com.todo.list.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebConfig {

    @Bean
    fun corsConfigurer(): WebFluxConfigurer =
        object : WebFluxConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")?.allowedOrigins("http://localhost:3000")?.allowedMethods("*")
            }
        }
}