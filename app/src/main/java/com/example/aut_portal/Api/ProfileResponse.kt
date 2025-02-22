package com.example.aut_portal.Api
import com.example.aut_portal.Entity.User

data class ProfileResponse(
    val status: String,
    val message: String,
    val data: User?
)

