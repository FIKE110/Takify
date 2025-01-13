package com.chat.fortunechatbackendwithspring.model

data class ConverseMessage(val eventType:ConverseEvents, val data: Map<String, ConversationResponse>)