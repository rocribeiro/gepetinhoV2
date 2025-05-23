package com.gepetinhoV2.controller.response

import com.gepetinhoV2.model.Text

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

