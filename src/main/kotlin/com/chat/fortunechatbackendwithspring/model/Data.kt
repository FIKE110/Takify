package com.chat.fortunechatbackendwithspring.model

enum class Status{
    success,
    error
}


data class LoginResponse(val status: Status,val message:String,val data:Map<String,Any>?)
data class ConversationResponse(val status: Status,val message:String,val data:Map<String,Any>)