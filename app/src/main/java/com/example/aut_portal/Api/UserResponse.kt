package com.example.aut_portal.Api

import com.example.aut_portal.Entity.UserEntity

data class UserResponse(
    val status: String,
    val success: Boolean,
    val UserData: List<UserEntity>
)
