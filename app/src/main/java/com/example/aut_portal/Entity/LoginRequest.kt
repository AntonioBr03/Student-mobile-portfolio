package com.example.aut_portal.Entity

data class LoginRequest(
    val action: Int = 3,
    val Std_ID: Int? = null,  // Optional, but ensure you're not including this if not necessary
    val Std_uname: String,
    val Std_Pass: String
)
