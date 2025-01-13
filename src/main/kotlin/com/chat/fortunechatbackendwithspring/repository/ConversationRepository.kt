package com.chat.fortunechatbackendwithspring.repository

import com.chat.fortunechatbackendwithspring.model.Conversation
import org.springframework.data.mongodb.repository.MongoRepository

interface ConversationRepository: MongoRepository<Conversation, String>  {

}