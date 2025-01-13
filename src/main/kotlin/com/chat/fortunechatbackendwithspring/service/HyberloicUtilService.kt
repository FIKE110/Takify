package com.chat.fortunechatbackendwithspring.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class HyberloicUtilService(private val webClient: WebClient) {


    @Value("\${hyperbolic.image.access-key}")
    private val accessKey: String? = null

    @Value("\${hyperbolic.image.model-name}")
    private val modelName: String? = null

    data class ImageGenerationRequest(
        val model_name: String = "SDXL1.0-base",
        val prompt: String = "a photo of an astronaut riding a horse on mars",
        val height: Int = 1024,
        val width: Int = 1024,
        val backend: String = "auto"
    )

    fun genImage(image:String) : WebClient.ResponseSpec {
        return webClient.post()
            .uri("/hp/$image")
            .header("Content-Type", "application/json")
            .header("Authorization","Bearer ${accessKey}")
            .bodyValue(ImageGenerationRequest())
            .retrieve()
    }
}