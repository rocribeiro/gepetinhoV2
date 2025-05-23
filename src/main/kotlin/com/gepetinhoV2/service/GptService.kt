package com.gepetinhoV2.service

import com.gepetinhoV2.client.chatgpt.GptClient
import com.gepetinhoV2.model.Gpt
import com.gepetinhoV2.model.MessageData
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GptService(
    private val gptClient: GptClient,
    @Value("\${gpt.message.model}") private val gptModel: String,
    @Value("\${gpt.message.role}") private val gptRole: String
) {

    fun generateResponse(userMessage: String): String? {
        val gptBody = Gpt(
            model = gptModel,
            messages = listOf(MessageData(content = userMessage, role = gptRole))
        )

        val gptResponse = gptClient.sendMessage(gptBody)
        val gptReply = gptResponse.choices.firstOrNull()?.message?.content
            ?: "Opss! tivemos um problema, tente novamente mais tarde!"

        return gptReply

    }
}
