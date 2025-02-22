package com.example.aut_portal.Entity

data class PostWithComment(
    val post: PostEntity,
    val comments: List<CommentEntity>

)
