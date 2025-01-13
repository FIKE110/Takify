package com.chat.fortunechatbackendwithspring.model

import org.springframework.data.annotation.Id

data class WsMessage(
    val message:String,val timestamp:Long,
    val conversationId:String
)