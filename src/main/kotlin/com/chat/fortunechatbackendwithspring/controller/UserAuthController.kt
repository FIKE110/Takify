package com.chat.fortunechatbackendwithspring.controller

import com.chat.fortunechatbackendwithspring.model.*
import com.chat.fortunechatbackendwithspring.service.UserService
import com.chat.fortunechatbackendwithspring.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/auth")
@RestController
class UserAuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val authentionProvider: AuthenticationProvider,
    private val jwtUtil: JwtUtil
) {


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid registerRequest: UserRegistrationRequest): ResponseEntity<String> {
        val (username,displayName,email,password)=registerRequest
        val profileImgService=userService.getRandomProfileImage()
        val user=userService.addUser(User(
            username, password=passwordEncoder.encode(password), email, displayName, friends =  mutableSetOf(),
            profileImageUrl = profileImgService
        ))

        return ResponseEntity<String>("User created",HttpStatus.CREATED)
    }

    @GetMapping("/users")
    fun getAllUsers(@RequestParam("username") username: String): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getUserByUsername(username).get())
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: UserLoginRequest): ResponseEntity<Any> {

        var token:String?=null
        try {
            val auth = authentionProvider
                .authenticate(
                    UsernamePasswordAuthenticationToken(
                        loginRequest.username,
                        loginRequest.password
                    )
                )
            if (auth != null) {
                token = jwtUtil.generateToken(loginRequest.username)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        println(token)
        return ResponseEntity
            .ok(
                if(token!=null)
                    LoginResponse(status = Status.success,
                        message="login successful",
                        data= mapOf(Pair("token",token))
                    )
                else LoginResponse(status = Status.error,
                    message = "Invalid Username and Password",
                    data=null
                ))
    }
}