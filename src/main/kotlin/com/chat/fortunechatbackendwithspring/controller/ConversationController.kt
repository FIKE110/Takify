package com.chat.fortunechatbackendwithspring.controller

import com.chat.fortunechatbackendwithspring.model.Conversation
import com.chat.fortunechatbackendwithspring.model.ConversationResponse
import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.service.ConversationService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/user/conversation")
class ConversationController(
    private val conversationService: ConversationService
) {

    @GetMapping("/list")
        fun listConversations(@AuthenticationPrincipal user: User): List<Conversation> {
            return conversationService.getAllUserConversations(user.getId())
        }

    @GetMapping("/list/details")
    fun listConversationDetails(@AuthenticationPrincipal user: User): List<ConversationResponse> {
        return conversationService.getAllUserConversationWithDetails(user.getId())
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{conversationId}/block")
    fun blockConversation(@AuthenticationPrincipal user: User, @PathVariable conversationId: String) {
        conversationService.blockConversation(conversationId=conversationId,
            blocked = true, userId = user.getId()
            )
    }

}