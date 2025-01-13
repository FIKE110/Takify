package com.chat.fortunechatbackendwithspring.config.ws

import com.chat.fortunechatbackendwithspring.model.Message
import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.service.MessageSenderService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.Date

@Component
class WsHandler(
    private val messageSenderService: MessageSenderService
) : TextWebSocketHandler() {
    private val sessionHolderList= mutableMapOf<String,WebSocketSession>()
    override fun handleTextMessage(session: org.springframework.web.socket.WebSocketSession, message: TextMessage) {

    }

    override fun afterConnectionEstablished(session: org.springframework.web.socket.WebSocketSession) {
        super.afterConnectionEstablished(session)
        val user:User=session.attributes.get("user") as User
        sessionHolderList.put(user.getId(),session)
    }

    override fun afterConnectionClosed(session: org.springframework.web.socket.WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        sessionHolderList.remove(session.getId())
        println(sessionHolderList)
    }

    fun sendMessageToSession(userId:String,message: Message){
        println("Sending message to user "+ userId+" "+message.getMessage())
        sendMessageToUser(userId,message)
        println("Message sent")
    }


    fun sendMessageToUser(userId: String,data:Any){
        sessionHolderList.forEach{
            if(userId==it.key){
                it.value.sendMessage(
                    TextMessage(
                        jacksonObjectMapper().writeValueAsString(data)))
            }
        }
    }

    fun sendEventToSession(userId:String,data:Any){
        sendMessageToUser(userId,data)
    }
}