package com.chat.fortunechatbackendwithspring.config.ws

import com.chat.fortunechatbackendwithspring.service.UserDaoService
import com.chat.fortunechatbackendwithspring.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Exception

@Component
class WebSocketHandShakeInterceptor(
    private val jwtUtil: JwtUtil,
    private val userDaoService: UserDaoService
) : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        println("hello")
        val uri=request.uri
        val queryParams=UriComponentsBuilder.fromUri(uri).build().queryParams.toSingleValueMap()
        val token=queryParams.get("token")
        if(token!=null && jwtUtil.validateToken(token)){
            val username=jwtUtil.extractUsername(token)
            val userDetails=userDaoService.loadUserByUsername(username)
            attributes["token"] = token
            attributes["user"]=userDetails?:"null"

            return true
        }

        return false
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {

    }

}