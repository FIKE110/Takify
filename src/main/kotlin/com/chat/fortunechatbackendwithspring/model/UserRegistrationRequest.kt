package com.chat.fortunechatbackendwithspring.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class UserRegistrationRequest(
    @NotBlank(message = "Username cannot be blank")
    val username: String,
    @NotBlank(message = "Display name cannot be blank")
    val displayName: String,
    @Email(message = "Email cannot be blank")
    val email: String,
    @Min(value = 6, message = "Password cannot be less than or equal to 6 characters")
    @NotBlank(message = "Password cannot be blank")
    val password: String
)

data class UserLoginRequest(
    val username: String,
    val password: String
)