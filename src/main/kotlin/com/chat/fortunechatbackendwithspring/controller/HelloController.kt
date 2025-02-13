package com.chat.fortunechatbackendwithspring.controller

import com.chat.fortunechatbackendwithspring.exceptions.CohereChatException
import com.chat.fortunechatbackendwithspring.model.ChatHistory
import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.service.CohereUtilService
import com.cohere.api.Cohere
import com.cohere.api.requests.ChatRequest
import com.cohere.api.requests.ChatStreamRequest
import com.cohere.api.types.ChatMessage
import com.cohere.api.types.ChatTextGenerationEvent
import com.cohere.api.types.Message
import com.cohere.api.types.StreamedChatResponse
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.MessageSource
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.util.List


@RestController
@RequestMapping("/")

class HelloController(
    private val chatModel: Cohere,
    private val cohereUtilService: CohereUtilService,
) {

    @GetMapping("/index")
    fun hello() = "index"

    @GetMapping("/profile")
    fun profile(@AuthenticationPrincipal user:User):User{
        return user
    }


    @GetMapping("/chat")
    fun chat(@RequestParam("chat") chat: String): Flux<String> {
        try{
            val flux=cohereUtilService.sendChatRequest(userMessage = chat)
            return flux
        }
        catch(ex: Exception) {
            throw CohereChatException(message = ex.message?:"No message specified")
        }
    }

    @GetMapping("/chats")
    fun chatWithoutStream(@RequestParam("chat") chat: String): String {
        val chatResonse = chatModel.chat(ChatRequest.builder()
            .message(chat).build())
        return chatResonse.text
    }

    @GetMapping("/images")
    fun genImage(@RequestParam("image") image:String):String{
        return ""
    }

    @GetMapping("/streamed")
    fun chatStream(@RequestParam("chat") chat: String): Flux<StreamedChatResponse> {
        val response: Iterable<StreamedChatResponse> =
            chatModel.chatStream(
                ChatStreamRequest.builder()
                    .message("What year was he born?")
                    .chatHistory(
                        List.of(
                            Message.user(
                                ChatMessage.builder().message("Who discovered gravity?").build()
                            ),
                            Message.chatbot(
                                ChatMessage.builder()
                                    .message(
                                        "The man who is widely"
                                                + " credited with"
                                                + " discovering gravity"
                                                + " is Sir Isaac"
                                                + " Newton"
                                    )
                                    .build()
                            )
                        )
                    )
                    .build()
            )
        for (chatResponse: StreamedChatResponse in response) {
            if (chatResponse.isTextGeneration) {
                println(
                    chatResponse.textGeneration.map({ obj: ChatTextGenerationEvent -> obj.getText() }).orElse("")
                )
            }
        }
        println(response)
        return Flux.fromIterable(response)
    }
}

