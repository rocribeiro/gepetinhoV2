package com.gepetinhoV2.client.whatsapp

import com.gepetinhoV2.client.whatsapp.config.WhatsAppClientConfig
import com.gepetinhoV2.model.WhatsApp
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping


@FeignClient(
    name = "whatsClient",
    url = "\${whats.message.url}",
    configuration = [WhatsAppClientConfig::class]
)
interface WhatsAppClient {
    @PostMapping
    fun sendMessage(whatsBody: WhatsApp?): ResponseEntity<MutableMap<*, *>?>?
}