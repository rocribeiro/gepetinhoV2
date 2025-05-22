package com.gepetinho_20.controller.response

data class GptResponse(
    val choices: List<Choice>
)
data class Choice(
    val message: GptMessage
)
data class GptMessage(
    val content: String
)
