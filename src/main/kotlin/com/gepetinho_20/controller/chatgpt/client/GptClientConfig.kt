package com.gepetinho_20.controller.chatgpt.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class GptClientConfig {
    @Value("\${gpt.token}")
    private val gptToken: String? = null

    @Bean
    fun gptInterceptor(): RequestInterceptor {
        return RequestInterceptor { template: RequestTemplate? ->
            template!!.header("Content-Type", "application/json")
            template.header("Authorization", gptToken)
        }
    }
}