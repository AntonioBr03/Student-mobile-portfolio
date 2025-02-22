package com.example.aut_portal.Api

import com.example.aut_portal.Entity.PostEntity

data class PostResponse(
    val status: String,
    val success: Boolean,
    val postdata: List<PostEntity>
)
