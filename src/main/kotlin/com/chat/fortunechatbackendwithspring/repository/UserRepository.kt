package com.chat.fortunechatbackendwithspring.repository

import com.chat.fortunechatbackendwithspring.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findByUsername(username: String): Optional<User>
}