package com.example.aut_portal.Entity

data class UserEntity(
    val Std_ID: Int,
    val Std_Name: String,
    val Std_email: String,
    val Std_Pass: String?,
    val Std_Major: String,
    val Std_YearOfEnrollment: Int?,
    val Std_ProfPic: String
)
