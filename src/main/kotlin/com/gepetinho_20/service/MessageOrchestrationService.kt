package com.gepetinho_20.service

import com.gepetinho_20.controller.response.WhatsAppWebhookResponse
import org.springframework.stereotype.Service

@Service
class MessageOrchestrationService(
    private val whatsAppService: WhatsAppService,
    private val gptService: GptService
) {

    fun processWebhook(body: WhatsAppWebhookResponse) {
        val messageInfo = whatsAppService.extractMessage(body) ?: return

        val gptReply = gptService.generateResponse(messageInfo.message) ?: return

        whatsAppService.sendMessage(messageInfo.from, gptReply)
    }
}