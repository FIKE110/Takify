package com.chat.fortunechatbackendwithspring.model

data class ChatHistory(
    val id:String?,val type:String, val delta:Map<String,Any>
)
