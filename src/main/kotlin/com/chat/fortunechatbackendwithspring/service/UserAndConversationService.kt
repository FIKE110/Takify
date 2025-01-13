
package com.chat.fortunechatbackendwithspring.service
import com.chat.fortunechatbackendwithspring.model.User

import com.chat.fortunechatbackendwithspring.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserAndConversationService(
    private val conversationService: ConversationService,
    private val userRepository: UserRepository
) {

    fun getAllUsersThatAllNotYourFriends(userId:String):List<User>{
            val friends=conversationService.getAllUsersWithConversation(userId = userId)
            val users=userRepository.findAll()
            val userList=mutableListOf<User>()
            for(user in users){
                var isFriend=false
                for(friend in friends){
                    if(user.getId().equals(friend.getId())){
                        isFriend=true
                        break
                    }
                }
                if(!isFriend){
                    userList.add(user)
                }
            }
        println(friends)
        println(users)
        println(userList)
            return userList
    }
}