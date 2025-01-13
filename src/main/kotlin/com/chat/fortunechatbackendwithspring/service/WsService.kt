package com.chat.fortunechatbackendwithspring.service

import com.chat.fortunechatbackendwithspring.config.ws.WsHandler
import com.chat.fortunechatbackendwithspring.model.Message
import org.springframework.stereotype.Service

@Service
class WsService(private val wsHandler: WsHandler) {

    fun sendMessageToSession(receivers:List<String>,message:Message){
        receivers.forEach{
            wsHandler.sendMessageToSession(it,message)
        }
    }
}