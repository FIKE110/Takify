package com.chat.fortunechatbackendwithspring.service

import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.repository.UserRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.*
import kotlin.random.Random

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mongoTemplate: MongoTemplate,
    private val restTemplate: RestTemplate,
) {

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getFriends(userId: String): MutableSet<String> {
        return getUser(userId).getFriends()
    }

    fun setUserProfilePicture(userId:String,imageUrl:String){
        val userOptional=userRepository.findById(userId)
        if(userOptional.isPresent){
            val user=userOptional.get()
            user.setProfileImageUrl(profileImageUrl = imageUrl)
            userRepository.save(user)
        }
    }

    fun getRandomProfileImage():String{
        val randomImageUrl="https://avatar.iran.liara.run/public/${Random.nextInt(100)}"
        return randomImageUrl
    }

    fun addFriend(userId:String, friendId:String) : Boolean{
        //if(!userExistsById(friendId)) return false
        val query=Query(Criteria.where("_id").`is`(userId))
        val update= Update().addToSet("friends",friendId)
        mongoTemplate.updateFirst(query, update, User::class.java)
        return true
    }

    fun removeFriend(userId:String, friendId:String) {
        if(!userExistsById(friendId)) return
        val query=Query(Criteria.where("_id").`is`(userId))
        val update= Update().pull("friends",friendId)
        mongoTemplate.updateFirst(query, update,User::class.java)
    }

    fun userExistsById(id: String): Boolean {
        return userRepository.existsById(id)
    }

    fun userExists(username: String): Boolean {
        return getUserByUsername(username).isPresent
    }

    fun addUser(user: User) : User{
            return userRepository.save(user)
    }

    fun removeUser(user: User) {
        userRepository.delete(user)
    }

    fun getUserByUsername(username: String): Optional<User> {
        return userRepository.findByUsername(username)
    }

    fun getUser(id: String): User {
        return userRepository.findById(id).get()
    }
}