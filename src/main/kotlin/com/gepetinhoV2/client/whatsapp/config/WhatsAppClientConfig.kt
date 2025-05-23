package com.gepetinhoV2.client.whatsapp.config

import feign.RequestInterceptor
import feign.RequestTemplate
import feign.codec.ErrorDecoder
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

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return WhatsappErrorDecoder()
    }
}