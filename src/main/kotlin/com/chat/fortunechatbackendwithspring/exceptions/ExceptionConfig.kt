package com.chat.fortunechatbackendwithspring.exceptions

import com.cohere.api.Cohere
import org.springframework.ai.chat.client.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionConfig {

    @ExceptionHandler(CohereChatException::class)
    public fun cohereChatException(e: CohereChatException): ResponseEntity<String,HttpStatus> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}