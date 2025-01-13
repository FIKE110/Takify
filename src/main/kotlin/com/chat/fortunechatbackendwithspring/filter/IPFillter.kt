package com.chat.fortunechatbackendwithspring.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import java.util.logging.Level
import java.util.logging.Logger

class IPFillter : OncePerRequestFilter(){
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        Logger.getLogger(IPFillter::class.java.name)
            .log(Level.INFO,
            request.remoteAddr?:""
            );
        filterChain.doFilter(request, response)
    }
}