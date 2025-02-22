package com.example.aut_portal.Api

data class LoginResponse(
    val user_id: Int? = null,  // Nullable
    val Std_uname: String?,
    val status: String?,
    val message: String?
)

