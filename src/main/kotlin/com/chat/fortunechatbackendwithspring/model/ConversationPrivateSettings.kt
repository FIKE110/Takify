package com.chat.fortunechatbackendwithspring.model


data class ConversationPrivateSettings(
    val userId:String,val archived:Boolean,val blocked:Boolean)