package com.example.aut_portal

import com.example.aut_portal.Api.FileUploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileUploadService {
    @Multipart
    @POST("AutPortal.php") // Replace with your API endpoint
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Call<FileUploadResponse>
}
