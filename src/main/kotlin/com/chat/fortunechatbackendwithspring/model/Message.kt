package com.chat.fortunechatbackendwithspring.model

import org.springframework.data.annotation.Id

class Message{
    @Id
    private lateinit var id:String
    private val message:String
    private val timestamp:Long
    private val senderId:String
    private val conversationId:String
    public var isRead:Boolean=false
    public var isSent:Boolean=false

    fun getId():String{
        return id
    }

    fun getTimestamp():Long{
        return timestamp
    }

    fun getSenderId():String{
        return senderId
    }
    fun getConversationId():String{
        return conversationId
    }


    constructor(message:String, timestamp:Long, senderId:String, conversationId:String,isRead:Boolean,isSent:Boolean){
        this.message = message
        this.timestamp = timestamp
        this.senderId = senderId
        this.conversationId = conversationId
        this.isRead=isRead
        this.isSent=isSent
    }


    fun getMessage():String{
        return message
    }
}
