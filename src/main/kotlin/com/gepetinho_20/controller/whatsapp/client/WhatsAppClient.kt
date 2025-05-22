package com.gepetinho_20.controller.whatsapp.client

import com.gepetinho_20.model.WhatsApp
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping


@FeignClient(name = "whatsClient", url = "\${whats.message.url}", configuration = [WhatsAppClientConfig::class])
interface WhatsAppClient {
    @PostMapping
    fun sendMessage(whatsBody: WhatsApp?): ResponseEntity<MutableMap<*, *>?>?
}