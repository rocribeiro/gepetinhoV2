package com.gepetinho_20.service

import com.gepetinho_20.client.chatgpt.GptClient
import com.gepetinho_20.model.Gpt
import com.gepetinho_20.model.MessageData
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory

@Service
class GptService(
    private val gptClient: GptClient,
    @Value("\${gpt.message.model}") private val gptModel: String,
    @Value("\${gpt.message.role}") private val gptRole: String
) {
    private val logger = LoggerFactory.getLogger(GptService::class.java)

    fun generateResponse(userMessage: String): String? {
        val gptBody = Gpt(
            model = gptModel,
            messages = listOf(MessageData(content = userMessage, role = gptRole))
        )

        val gptResponse = gptClient.sendMessage(gptBody)
        val gptReply = gptResponse.choices.firstOrNull()?.message?.content

        return (if (gptReply.isNullOrBlank()) {
            logger.warn("Resposta do GPT vazia para a mensagem: $userMessage")
        } else {
            gptReply
        }) as String
    }
}
