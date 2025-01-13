package com.chat.fortunechatbackendwithspring.config.ai

import com.cohere.api.Cohere
import com.cohere.api.resources.v2.requests.V2ChatRequest
import com.cohere.api.types.ChatMessageV2
import com.cohere.api.types.UserMessage
import com.cohere.api.types.UserMessageContent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CohereAiConfig {

    @Value("\${cohere.access-key}")
    lateinit var apiToken: String

    @Value("\${cohere.model}")
    lateinit var modelname: String



    @Bean
    fun getCohere():Cohere{

       return Cohere
            .builder()
            .token(apiToken)
            .clientName("converse")
            .build()
    }

    @Bean
    fun webClientBuilderBean():WebClient{
        val webClientBuilder=WebClient.builder()
        return webClientBuilder
            .build()
    }

}