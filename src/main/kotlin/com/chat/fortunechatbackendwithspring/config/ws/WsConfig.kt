package com.chat.fortunechatbackendwithspring.config.ws

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WsConfig(
    private val wsHandler: WsHandler,
    private val webSocketHandShakeInterceptor: WebSocketHandShakeInterceptor
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(wsHandler,"/ws/message")
            .addInterceptors(webSocketHandShakeInterceptor)
            .setAllowedOrigins("*")
    }
}