package com.gepetinhoV2.service

import com.gepetinhoV2.controller.response.Change
import com.gepetinhoV2.controller.response.Entry
import com.gepetinhoV2.controller.response.Message
import com.gepetinhoV2.controller.response.Value
import com.gepetinhoV2.model.Text
import com.gepetinhoV2.client.whatsapp.WhatsAppClient
import com.gepetinhoV2.controller.response.WhatsAppWebhookResponse

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WhatsAppServiceTest {
        private val whatsAppClient = mockk<WhatsAppClient>()
        private val whatsAppService = WhatsAppService(whatsAppClient)


        @Test
        fun `extractMessage should return MessageInfo when valid message is present`() {
            val text = Text(body = "Hello")
            val message = Message(from = "12345", text = text)
            val value = Value(messages = listOf(message))
            val change = Change(value = value)
            val entry = Entry(changes = listOf(change))
            val webhookResponse = WhatsAppWebhookResponse(entry = listOf(entry))

            val result = whatsAppService.extractMessage(webhookResponse)

            assertEquals("Hello", result?.message)
            assertEquals("12345", result?.from)
        }

        @Test
        fun `extractMessage should return null and log warning when message body is blank`() {
            val text = Text(body = "")
            val message = Message(from = "12345", text = text)
            val value = Value(messages = listOf(message))
            val change = Change(value = value)
            val entry = Entry(changes = listOf(change))
            val webhookResponse = WhatsAppWebhookResponse(entry = listOf(entry))

            val result = whatsAppService.extractMessage(webhookResponse)

            assertNull(result)
        }

        @Test
        fun `extractMessage should return null and log warning when from is blank`() {
            val text = Text(body = "Hi")
            val message = Message(from = "", text = text)
            val value = Value(messages = listOf(message))
            val change = Change(value = value)
            val entry = Entry(changes = listOf(change))
            val webhookResponse = WhatsAppWebhookResponse(entry = listOf(entry))

            val result = whatsAppService.extractMessage(webhookResponse)

            assertNull(result)
        }
}