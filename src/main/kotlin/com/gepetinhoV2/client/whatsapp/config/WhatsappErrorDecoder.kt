package com.gepetinhoV2.client.whatsapp.config

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus

class WhatsappErrorDecoder: ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        return when (response.status()) {
            HttpStatus.UNAUTHORIZED.value() -> RuntimeException("Não foi possível autenticar na API do whatsapp")
            else -> RuntimeException("Erro ao chamar a API do whatsapp")
        }
    }
}