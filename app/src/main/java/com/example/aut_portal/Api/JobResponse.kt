package com.example.aut_portal.Api

import com.example.aut_portal.Entity.JobEntity

data class JobResponse(
    val status: String,
    val success: Boolean,
    val data: List<JobEntity>
)

