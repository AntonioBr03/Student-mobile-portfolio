package com.example.aut_portal.Api

import com.example.aut_portal.Entity.CommentEntity
import com.example.aut_portal.Entity.JobEntity
import com.example.aut_portal.Entity.LoginRequest
import com.example.aut_portal.Entity.PostEntity
import com.example.aut_portal.Entity.ProfileData
import com.example.aut_portal.Entity.UserEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("AutPortal.php")
    fun getJobListings(
        @Query("action") action: Int
    ): Call<List<JobEntity>>
    @GET("AutPortal.php")
    fun getPosts(
        @Query("action") action:Int
    ): Call<List<PostEntity>>
    @GET("AutPortal.php")
    fun getComments(@Query("action")action:Int): Call<List<CommentEntity>>
    @POST("AutPortal.php?action=3")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
        @GET("AutPortal.php")
        fun fetchProfile(@Query("action") action: Int, @Query("Std_ID") stdId: Int): Call<ProfileResponse>
    }

