package com.gepetinhoV2.service

import com.gepetinhoV2.client.whatsapp.WhatsAppClient
import com.gepetinhoV2.controller.response.WhatsAppWebhookResponse
import com.gepetinhoV2.model.MessageInfo
import com.gepetinhoV2.model.Text
import com.gepetinhoV2.model.WhatsApp
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WhatsAppService(
    private val whatsAppClient: WhatsAppClient
) {

    private val logger = LoggerFactory.getLogger(WhatsAppService::class.java)

    fun extractMessage(body: WhatsAppWebhookResponse): MessageInfo? {
        val messageData = body.entry.firstOrNull()
            ?.changes?.firstOrNull()
            ?.value?.messages?.firstOrNull()

        val userMessage = messageData?.text?.body
        val from = messageData?.from

        return if (userMessage.isNullOrBlank() || from.isNullOrBlank()) {
            logger.warn("Mensagem ou remetente inválido. message=$userMessage, from=$from")
            null
        } else {
            MessageInfo(userMessage, from)
        }
    }

    fun sendMessage(to: String, message: String) {
        val whatsBody = WhatsApp(
            messaging_product = "whatsapp",
            to = to,
            text = Text(body = message)
        )
        whatsAppClient.sendMessage(whatsBody)
    }
}
