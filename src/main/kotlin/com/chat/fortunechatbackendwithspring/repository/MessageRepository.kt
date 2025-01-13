package com.chat.fortunechatbackendwithspring.repository

import com.chat.fortunechatbackendwithspring.model.Message
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<Message, String> {
    public fun findByConversationId(conversationId: String): List<Message>
}