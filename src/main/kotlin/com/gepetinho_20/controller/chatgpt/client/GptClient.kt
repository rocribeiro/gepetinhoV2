package com.gepetinho_20.controller.chatgpt.client

import com.gepetinho_20.controller.chatgpt.GptResponse
import com.gepetinho_20.model.Gpt
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "gptClient", url = "\${gpt.mesage.request.url}", configuration = [GptClientConfig::class])
interface GptClient {
    @PostMapping
    fun sendMessage(body: Gpt?): GptResponse?
}