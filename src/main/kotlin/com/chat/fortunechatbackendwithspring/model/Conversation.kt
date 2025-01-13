package com.chat.fortunechatbackendwithspring.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "conversation")
class Conversation {
    @Id
    private lateinit var id:String
    private lateinit var name:String
    private var blocked=false
    private var archived=false
    private lateinit var roomType:ConversationType
    private lateinit var participants:MutableSet<String>
    //private val info:String="This is just a normal room"
    private val settings:MutableMap<String,ConversationPrivateSettings>

    constructor(name:String, blocked:Boolean,roomType:ConversationType,participants:MutableSet<String>,settings:MutableMap<String,ConversationPrivateSettings>) {
        this.name = name
        this.blocked=blocked
        this.roomType=roomType
        this.participants= participants
        this.settings=settings
    }

    fun getName():String{
        return name
    }

    fun getBlocked():Boolean{
        return blocked
    }
    fun getRoomType():ConversationType{
        return roomType
    }
    fun getId():String{
        return id
    }
    fun getParticipants():MutableSet<String>{
        return participants
    }

    fun getSettings():MutableMap<String,ConversationPrivateSettings>{
        return settings
    }
}

enum class ConversationType{
    PRIVATE,
    ROOM
}