package com.example.aut_portal

import android.util.Log
import com.example.aut_portal.Api.JobResponse
import com.example.aut_portal.Api.ApiService
import com.example.aut_portal.Api.LoginResponse
import com.example.aut_portal.Api.PostResponse
import com.example.aut_portal.Api.ProfileResponse
import com.example.aut_portal.Api.UserResponse
import com.example.aut_portal.Entity.CommentEntity
import com.example.aut_portal.Entity.JobEntity
import com.example.aut_portal.Entity.LoginRequest
import com.example.aut_portal.Entity.PostEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitClient {

    private val retrofitInstance: Retrofit by lazy {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("http://192.168.0.103/AutPortal/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService = retrofitInstance.create(ApiService::class.java)



    fun getJobListings(action: Int, callback: (List<JobEntity>?) -> Unit) {
        val call = apiService.getJobListings(action)

        call.enqueue(object : Callback<List<JobEntity>> {
            override fun onResponse(
                call: Call<List<JobEntity>>,
                response: Response<List<JobEntity>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val jobListings = response.body()
                    callback(jobListings)  // Pass the job listings to the callback
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<JobEntity>>, t: Throwable) {
                Log.e("RetrofitClient", "Request failed", t)
                callback(null)  // Pass null on failure
            }
        })
    }
    fun fetchPosts(action: Int, callback: (List<PostEntity>?) -> Unit) {
        val call = apiService.getPosts(action)

        call.enqueue(object : Callback<List<PostEntity>> {
            override fun onResponse(
                call: Call<List<PostEntity>>,
                response: Response<List<PostEntity>>
            ) {
                if (response.isSuccessful) {
                    // Log the raw response body
                    Log.d("RetrofitClient", "Raw JSON: ${response.body()}")

                    val apiResponse = response.body()
                    callback(apiResponse) // Pass the parsed response to the callback
                } else {
                    // Handle error and log the raw error body
                    val errorBody = response.errorBody()?.string()
                    Log.e("RetrofitClient", "Error Body: $errorBody")
                    callback(null) // Pass null if the response is unsuccessful
                }
            }

            override fun onFailure(call: Call<List<PostEntity>>, t: Throwable) {
                // Log the error message for debugging
                Log.e("RetrofitClient", "Request failed: ${t.message}")
                callback(null) // Pass null on failure
            }
        })
    }
    fun getComment(action: Int, callback: (List<CommentEntity>?) -> Unit) {
        val call = apiService.getComments(action)

        call.enqueue(object : Callback<List<CommentEntity>> {
            override fun onResponse(
                call: Call<List<CommentEntity>>,
                response: Response<List<CommentEntity>>
            ) {
                if (response.isSuccessful) {
                    // Log the raw response body
                    Log.d("RetrofitClient", "Raw JSON: ${response.raw()}")

                    val apiResponse = response.body()
                    callback(apiResponse) // Pass the parsed response to the callback
                } else {
                    // Handle error and log the raw error body
                    val errorBody = response.errorBody()?.string()
                    Log.e("RetrofitClient", "Error Body: ${response.errorBody()?.string()}")
                    callback(null) // Pass null if the response is unsuccessful
                }
            }

            override fun onFailure(call: Call<List<CommentEntity>>, t: Throwable) {
                // Log the error message for debugging
                Log.e("RetrofitClient", "Request failed: ${t.message}")
                callback(null) // Pass null on failure
            }
        })
    }
    fun authenticateUser(loginRequest: LoginRequest, callback: (LoginResponse?) -> Unit) {
        val call = apiService.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("LoginResponse", "Raw response: $responseBody")  // Log the full response
                        callback(responseBody)
                    } else {
                        Log.e("LoginError", "Response body is null")
                        callback(null)
                    }
                } else {
                    Log.e("LoginError", "Error Response: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginError", "Request failed: ${t.message}")
                callback(null)
            }
        })
    }
    fun fetchProfile(Std_ID: Int, callback: (ProfileResponse?) -> Unit) {
        val call = apiService.fetchProfile(action = 5, stdId = Std_ID)


        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("RetrofitClient", "Error fetching profile: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("RetrofitClient", "Error fetching profile", t)
                callback(null)
            }
        })
    }






}