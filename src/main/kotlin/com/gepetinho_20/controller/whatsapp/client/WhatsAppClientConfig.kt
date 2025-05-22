package com.gepetinho_20.controller.whatsapp.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class WhatsAppClientConfig {
    @Value("\${whats.token}")
    private val whatsToken: String? = null

    @Bean
    fun whatsInterceptor(): RequestInterceptor {
        return RequestInterceptor { template: RequestTemplate? ->
            template!!.header("Content-Type", "application/json")
            template.header("Authorization", whatsToken)
        }
    }
}