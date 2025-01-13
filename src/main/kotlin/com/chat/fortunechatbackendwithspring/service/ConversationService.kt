package com.chat.fortunechatbackendwithspring.service

import com.chat.fortunechatbackendwithspring.config.ws.WsHandler
import com.chat.fortunechatbackendwithspring.model.*
import com.chat.fortunechatbackendwithspring.repository.ConversationRepository
import com.chat.fortunechatbackendwithspring.repository.UserRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.util.*

@Service
class ConversationService(
    private val conversationRepository: ConversationRepository,
    private val mongoTemplate: MongoTemplate,
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val wsHandler: WsHandler,
) {



    fun createNewConversation(userId: String, friendId:String, participants: MutableSet<String>?) {
        val conversation = Conversation(
            name = UUID.randomUUID().toString(),
            participants = participants ?: mutableSetOf(),
            blocked = false,
            roomType = ConversationType.PRIVATE,
            settings = mutableMapOf(Pair(userId,ConversationPrivateSettings(
                blocked = false, archived = false, userId = userId
            )),Pair(friendId,ConversationPrivateSettings(blocked = false, archived = false, userId = friendId)))
        )

        for(conversation in getAllUserConversations(userId)){
            val participants=conversation.getParticipants()
            if(participants.contains(friendId)){
             return
            }
        }

        val newConversation=conversationRepository.save(conversation)
        if(newConversation!=null){
            val friendUser=userService.getUser(friendId)
            val converseEventMessage=ConverseMessage(
                ConverseEvents.CONVERSATION_CREATED,
                data = mapOf(Pair("conversation",ConversationResponse(
                    status = Status.success,
                    message = "Friends name is ${friendUser.username}",
                    data = mapOf(
                        Pair("id",newConversation.getId()),
                        Pair("friendId", friendId),
                        Pair("username", friendUser.username),
                        Pair("time", 12000),
                        Pair("lastMessage","Hello there"),
                    )
                ))
            ))
           wsHandler.sendEventToSession(friendId,converseEventMessage)
        }
    }

    fun getAllUserConversations(userId: String): List<Conversation> {
        val query = Query(Criteria.where("participants").`in`(userId))
        return mongoTemplate.find(query, Conversation::class.java)
    }

    fun getAllParticipantsInConversation(conversationId: String): List<String> {
        return conversationRepository.findById(conversationId)
            .get().getParticipants().toList()
    }

    fun getAllUsersWithConversation(userId: String):List<User> {
        val friends=getAllUserConversations(userId).map { conversation ->
            conversation.getParticipants().remove(userId)
            userService.getUser(conversation.getParticipants().first())
        }

        return friends
    }

    fun getAllUserConversationWithDetails(userId: String): List<ConversationResponse> {
        return getAllUserConversations(userId).map {
                val friendId=it.getParticipants().filter { id -> id != userId }.first()
                val friendUser=userService.getUser(friendId)
                ConversationResponse(
                    status = Status.success,
                    message = "Friends name is ${friendUser.username}",
                    data = mapOf(
                        Pair("id",it.getId()),
                        Pair("friendId", friendId),
                        Pair("username", friendUser.username),
                        Pair("time", 12000),
                        Pair("lastMessage","Hello there"),
                        Pair("url",friendUser.getProfileImageUrl()),
                        Pair("settings",it.getSettings().get(userId) as Any)
                    )
                )
        }
    }

    fun blockConversation(conversationId: String,userId:String,blocked:Boolean) {
        conversationRepository.findById(conversationId).ifPresent {

        }
    }

    fun archiveConversation(conversationId: String) {

    }

}