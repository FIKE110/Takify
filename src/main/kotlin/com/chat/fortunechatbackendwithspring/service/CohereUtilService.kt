package com.chat.fortunechatbackendwithspring.service

import com.chat.fortunechatbackendwithspring.repository.TestRepositoryService
import com.cohere.api.resources.v2.requests.V2ChatRequest
import com.cohere.api.resources.v2.requests.V2ChatStreamRequest
import com.cohere.api.types.ChatMessage
import com.cohere.api.types.ChatMessageV2
import com.cohere.api.types.UserMessage
import com.cohere.api.types.UserMessageContent
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux


@Service
class CohereUtilService (private val webClient: WebClient ,private val testRepositoryService: TestRepositoryService){

    @Value("\${cohere.model}")
    lateinit var modelname: String

    @Value("\${cohere.api}")
    lateinit var api:String

    @Value("\${cohere.access-key}")
    lateinit var accessKey:String

    fun buildChatRequest(message:String):V2ChatStreamRequest{
        return V2ChatStreamRequest
            .builder()
            .model(modelname)
            .messages(
                listOf(ChatMessageV2
                    .user(
                        UserMessage
                            .builder()
                            .content(
                                UserMessageContent.of(
                                    message
                            )).build()
                    ))
            ).build()
    }


    fun sendChatRequest(model: String=modelname, userMessage: String, clientName: String?="Converse"): Flux<String> {
        val messages =
            //mapOf("role" to "user", "content" to userMessage)
            testRepositoryService.addNewMessageToHistory(
                    userMessage,true
            )
        val requestBody = mapOf(
            "model" to model,
            "messages" to messages,
            "stream" to true
        )

        return webClient
            .post()
            .uri(api)
            .headers { headers ->
                headers.setBearerAuth(accessKey)
                headers.contentType = MediaType.APPLICATION_JSON
                clientName?.let { headers.set("X-Client-Name", clientName) }
            }
            .bodyValue(requestBody)
            .retrieve()
            .bodyToFlux(String::class.java)
    }
}
