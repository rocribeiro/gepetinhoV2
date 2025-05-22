package com.gepetinho_20.client.chatgpt.config

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus

class GptErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        return when (response.status()) {
            HttpStatus.UNAUTHORIZED.value() -> RuntimeException("Não foi possível autenticar na API do GPT")
            else -> RuntimeException("Erro ao chamar a API do GPT")
        }
    }
}