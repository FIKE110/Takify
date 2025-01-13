package com.chat.fortunechatbackendwithspring.service

import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.repository.UserRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Service

@Service
class FriendService(val userRepository: UserRepository) {

    fun addFriend(userId:String,friendId:String,@AuthenticationPrincipal user: User){
        user.addFriend(userId);
        userRepository.save(user)
    }
}