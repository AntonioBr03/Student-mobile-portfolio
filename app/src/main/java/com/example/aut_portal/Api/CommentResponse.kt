package com.example.aut_portal.Api

import com.example.aut_portal.Entity.CommentEntity

data class CommentResponse(
    val status: String,
    val success: Boolean,
    val commentdata: List<CommentEntity>
)
