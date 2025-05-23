package com.gepetinhoV2.model

data class WhatsApp(
    val messaging_product: String,
    val to: String,
    val text: Text
)
data class Text(
    val body: String
)