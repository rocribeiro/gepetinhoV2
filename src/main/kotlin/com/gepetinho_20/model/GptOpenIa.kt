package com.gepetinho_20.model

data class Gpt(
    val model: String,
    val messages: List<MessageData>
)
data class MessageData(
    val role: String,
    val content: String
)