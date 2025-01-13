package com.chat.fortunechatbackendwithspring.repository

import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class TestRepositoryService {
    private var repository = mutableListOf<Map<String,String>>();

    init {
        println("Repository created")
    }

    fun addNewMessageToHistory(message:String,isUser:Boolean):List<Map<String,String>>{
        val contentDetails=  mapOf("role" to if(isUser) "user" else "chatbot","content" to message)
        repository.add(contentDetails)
        return repository
    }
}