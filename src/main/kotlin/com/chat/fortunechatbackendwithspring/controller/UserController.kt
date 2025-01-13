package com.chat.fortunechatbackendwithspring.controller

import com.chat.fortunechatbackendwithspring.model.Conversation
import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.service.ConversationService
import com.chat.fortunechatbackendwithspring.service.UserService
import org.springframework.ai.chat.client.ResponseEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import java.net.URI

@RestController
@RequestMapping("/api/user/friends")
class UserController(
    val userService: UserService,
    val conversationService: ConversationService
){

    @GetMapping("/list")
    fun listUsers(@AuthenticationPrincipal user:User): MutableSet<String> {
        return userService.getFriends(user.getId())
    }

    @GetMapping("/{friendId}/image")
    fun getUserProfile(@PathVariable friendId:String):RedirectView{
        val profileImageUrl=userService.getUser(friendId).getProfileImageUrl()
        val httpHeaders = HttpHeaders()
        httpHeaders.location=URI.create(profileImageUrl)
        return RedirectView(profileImageUrl)
        //return ResponseEntity(httpHeaders, HttpStatus.PERMANENT_REDIRECT)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{friendId}/add")
    fun addFriend(@AuthenticationPrincipal user: User,@PathVariable friendId:String) {
        if(userService.addFriend(user.getId(), friendId = friendId )){
            conversationService.createNewConversation(
                userId = user.getId() ,
                friendId=friendId,
                        mutableSetOf(user.getId(),friendId))
        }
    }

    @DeleteMapping("{friendId}/remove")
    fun removeFriend(@AuthenticationPrincipal user: User,@PathVariable friendId:String) {
        userService.removeFriend(user.getId(), friendId = friendId )
    }

}