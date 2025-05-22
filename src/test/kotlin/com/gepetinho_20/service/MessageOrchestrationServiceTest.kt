package com.gepetinho_20.service
import com.gepetinho_20.controller.response.WhatsAppWebhookResponse
import com.gepetinho_20.model.MessageInfo
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class MessageOrchestrationServiceTest {

    private val whatsAppService =  mockk<WhatsAppService>()
    private val gptService = mockk<GptService>()
    private val messageOrchestrationService = MessageOrchestrationService(whatsAppService,gptService)


    @Test
    fun `processWebhook should do nothing when extractMessage returns null`() {
        val webhookBody: WhatsAppWebhookResponse = mockk()
        every { whatsAppService.extractMessage(webhookBody) } returns null

        assertDoesNotThrow {
            messageOrchestrationService.processWebhook(webhookBody)
        }

        verify(exactly = 1) { whatsAppService.extractMessage(webhookBody) }
        verify(exactly = 0) { gptService.generateResponse(any()) }
        verify(exactly = 0) { whatsAppService.sendMessage(any(), any()) }
    }

    @Test
    fun `processWebhook should do nothing when generateResponse returns null`() {
        val webhookBody: WhatsAppWebhookResponse = mockk()
        val messageInfo = MessageInfo("from_number", "test message")

        every { whatsAppService.extractMessage(webhookBody) } returns messageInfo
        every { gptService.generateResponse(messageInfo.message) } returns null

        assertDoesNotThrow {
            messageOrchestrationService.processWebhook(webhookBody)
        }

        verify(exactly = 1) { whatsAppService.extractMessage(webhookBody) }
        verify(exactly = 1) { gptService.generateResponse(messageInfo.message) }
        verify(exactly = 0) { whatsAppService.sendMessage(any(), any()) }
    }

    @Test
    fun `processWebhook should send message when all steps are successful`() {
        val webhookBody: WhatsAppWebhookResponse = mockk()
        val messageInfo = MessageInfo("from_number", "test message")
        val gptReply = "GPT response"

        every { whatsAppService.extractMessage(webhookBody) } returns messageInfo
        every { gptService.generateResponse(messageInfo.message) } returns gptReply
        every { whatsAppService.sendMessage(messageInfo.from, gptReply) } just Runs

        messageOrchestrationService.processWebhook(webhookBody)

        verify(exactly = 1) { whatsAppService.extractMessage(webhookBody) }
        verify(exactly = 1) { gptService.generateResponse(messageInfo.message) }
        verify(exactly = 1) { whatsAppService.sendMessage(messageInfo.from, gptReply) }
    }
}
