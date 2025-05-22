package com.gepetinho_20.controller.response

import com.gepetinho_20.model.Text

data class WhatsAppWebhookResponse(
    val entry: List<Entry>
)
data class Entry(
    val changes: List<Change>
)
data class Change(
    val value: Value
)
data class Value(
    val messages: List<Message>
)
data class Message(
    val from: String,
    val text: Text
)

