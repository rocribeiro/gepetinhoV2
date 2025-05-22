package com.gepetinho_20.service

import com.gepetinho_20.model.MessageInfo
import com.gepetinho_20.controller.response.WhatsAppWebhookResponse
import com.gepetinho_20.client.whatsapp.WhatsAppClient
import com.gepetinho_20.model.Text
import com.gepetinho_20.model.WhatsApp
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory

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
