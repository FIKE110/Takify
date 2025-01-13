package com.chat.fortunechatbackendwithspring.filter

import com.chat.fortunechatbackendwithspring.service.UserDaoService
import com.chat.fortunechatbackendwithspring.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val userDaoService: UserDaoService,
    private val jwtUtil: JwtUtil,
): OncePerRequestFilter(){
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader: String? =
                if(!request.getHeader("Authorization").isNullOrBlank()) request.getHeader("Authorization")
                else "Bearer ${request.getParameter("token")}"
            println(authHeader)
            if (authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.startsWith("Bearer null")) {
                val token = authHeader.substring(7)
                if (jwtUtil.validateToken(token)) {
                    val token_username = jwtUtil.extractUsername(token)
                    if (token_username != null && SecurityContextHolder.getContext().authentication == null) {
                        val user = userDaoService.loadUserByUsername(token_username)
                        val authToken = UsernamePasswordAuthenticationToken(
                            user, null,user!!.authorities
                        )
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }
            }
        }
        catch (e: Exception) {
            println(e.message)
        }

        filterChain.doFilter(request, response)
    }
}