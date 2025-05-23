package com.gepetinhoV2.model

data class Gpt(
    val model: String,
    val messages: List<MessageData>
)
data class MessageData(
    val role: String,
    val content: String
)