package com.gepetinhoV2.client.chatgpt

import com.gepetinhoV2.client.chatgpt.config.GptClientConfig
import com.gepetinhoV2.controller.response.GptResponse
import com.gepetinhoV2.model.Gpt
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "gptClient",
    url = "\${gpt.mesage.request.url}",
    configuration = [GptClientConfig::class])

interface GptClient {
    @PostMapping
    fun sendMessage(body: Gpt): GptResponse
}