package com.chat.fortunechatbackendwithspring.controller

import com.chat.fortunechatbackendwithspring.model.Message
import com.chat.fortunechatbackendwithspring.model.MessageRequestBody
import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.service.ConversationService
import com.chat.fortunechatbackendwithspring.service.MessageSenderService
import com.chat.fortunechatbackendwithspring.service.WsService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user/message")
class MessageController(
    private val messageSenderService: MessageSenderService,
    private val wsService: WsService,
    private val conversationService: ConversationService
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{conversationId}")
    fun getMessage(@PathVariable conversationId: String) : List<Message> {
        return messageSenderService.getAllMessagesInConversation(conversationId)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{conversationId}")
    fun sendMessage(
        @AuthenticationPrincipal user:User,
        @PathVariable("conversationId") conversationId: String,@RequestBody messageBody:MessageRequestBody){
        val message=Message(
            conversationId = conversationId,
            message = messageBody.message,
            senderId = user.getId(),
            timestamp = messageBody.timestamp,
            isRead = false,
            isSent = false
        )
        print(message)
        val savedMessage=messageSenderService.saveMessageToConversation(message)
        var partipants=conversationService
            .getAllParticipantsInConversation(savedMessage.getConversationId())
        val particantsExcludingsender=partipants.filter {
            !it.equals(savedMessage.getSenderId())
        }
        wsService.sendMessageToSession(particantsExcludingsender,message)
    }
}