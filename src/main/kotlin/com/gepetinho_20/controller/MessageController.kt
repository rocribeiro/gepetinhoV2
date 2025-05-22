package com.gepetinho_20.controller

import com.gepetinho_20.controller.response.WhatsAppWebhookResponse
import com.gepetinho_20.service.MessageOrchestrationService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MessageController(private val messageOrchestrationService: MessageOrchestrationService) {

    @Value("\${whats.verification.token}")
    private val whatsVerificationToken: String? = null

    @GetMapping("/webhook")
    fun verifyWebhook(
        @RequestParam(name = "hub.mode") mode: String,
        @RequestParam(name = "hub.challenge") challenge: String,
        @RequestParam(name = "hub.verify_token") verifyToken: String
    ): ResponseEntity<String?>  =
        if (whatsVerificationToken == verifyToken)
            ResponseEntity.status(HttpStatus.OK).body<String?>(challenge)
        else
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build<String?>()

    @PostMapping("/webhook")
    fun listenerWebhook(@RequestBody body: WhatsAppWebhookResponse) =
        messageOrchestrationService.processWebhook(body)
}