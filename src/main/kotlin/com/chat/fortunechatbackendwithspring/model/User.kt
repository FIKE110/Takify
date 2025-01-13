package com.chat.fortunechatbackendwithspring.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


@Document(collection = "users")
class User():UserDetails{

    @Id
    private lateinit var id:String

    private lateinit var username:String
    private lateinit var password:String
    private lateinit var email:String
    private lateinit var displayName:String
    private lateinit var friends:MutableSet<String>
    private lateinit var profileImageUrl:String

    constructor(username:String, password:String, email:String, displayName:String,friends:
    MutableSet<String>,profileImageUrl:String
    ):this(){
        this.username = username
        this.password = password
        this.email = email
        this.displayName = displayName
        this.friends = friends
        this.profileImageUrl = profileImageUrl
    }

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        val simpleGrantedAuthority=SimpleGrantedAuthority("ROLE_USER")
        return Collections.singletonList(simpleGrantedAuthority)
    }

    fun setProfileImageUrl(profileImageUrl: String){
        this.profileImageUrl=profileImageUrl
    }

    fun addFriend(id:String){
        this.friends.add(id)
    }

    fun getFriends():MutableSet<String>{
        return friends
    }

    override fun getPassword(): String {
        return password;
    }

    fun getDisplayName():String{
        return displayName;
    }


    override fun getUsername(): String {
       return username
    }

    fun getId(): String {
        return id;
    }

    fun getEmail(): String {
        return email;
    }

    fun getProfileImageUrl():String{
        return profileImageUrl;
    }

}
