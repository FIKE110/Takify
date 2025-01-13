package com.chat.fortunechatbackendwithspring.service

import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class UserDaoService(private val userService: UserService):UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails? {
        if(username != null){
            return userService.getUserByUsername(username).get();
        }
        return null
    }

}