package com.gepetinhoV2.service

import com.gepetinhoV2.controller.response.WhatsAppWebhookResponse
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