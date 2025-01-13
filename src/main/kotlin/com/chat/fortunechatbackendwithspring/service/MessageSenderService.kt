package com.chat.fortunechatbackendwithspring.service

import com.chat.fortunechatbackendwithspring.model.Message
import com.chat.fortunechatbackendwithspring.repository.MessageRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service

@Service
class MessageSenderService(private val messageRepository: MessageRepository) {
    private val mapper = jacksonObjectMapper()
    fun sendMessage(message: String) {
        val parsedMessage=mapper.readValue<Message>(message, Message::class.java)
        messageRepository.save(parsedMessage)
        println("message is saved ${parsedMessage.getMessage()}")
    }

    fun saveMessageToConversation(message: Message) :Message{
        val savedMessage=messageRepository.save(message)
        return savedMessage
    }

    fun getAllMessagesInConversation(conversationId:String): List<Message> {
        return messageRepository.findByConversationId(conversationId)
    }
}