package com.gepetinho_20.service

import com.gepetinho_20.client.chatgpt.GptClient
import com.gepetinho_20.controller.response.Choice
import com.gepetinho_20.controller.response.GptMessage
import com.gepetinho_20.controller.response.GptResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class GptServiceTest {

    private val gptClient = mockk<GptClient>()
    private val gptModel = "gpt-3.5-turbo"
    private val gptRole = "user"

    private val gptService = GptService(gptClient, gptModel, gptRole)

    @Test
    fun `generateResponse should return formatted response when API call is successful`() {
        val userMessage = "Olá, como vai?"
        val expectedResponse = "Estou bem, obrigado por perguntar!"
        val mockGptResponse = GptResponse(
            choices = listOf(
                Choice(
                    message = GptMessage(
                        content = expectedResponse
                    )
                )
            )
        )

        every { gptClient.sendMessage(any()) } returns mockGptResponse

        val result = gptService.generateResponse(userMessage)

        assertEquals(expectedResponse, result)
        verify { gptClient.sendMessage(any()) }
    }

    @Test
    fun `generateResponse should return default error message when choices list is empty`() {
        val userMessage = "Olá, como vai?"
        val mockGptResponse = GptResponse(choices = emptyList())
        val expectedErrorMessage = "Opss! tivemos um problema, tente novamente mais tarde!"

        every { gptClient.sendMessage(any()) } returns mockGptResponse

        val result = gptService.generateResponse(userMessage)

        assertEquals(expectedErrorMessage, result)
        verify { gptClient.sendMessage(any()) }
    }

}
