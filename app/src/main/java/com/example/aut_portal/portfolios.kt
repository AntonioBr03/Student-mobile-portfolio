package com.example.aut_portal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Button
import com.example.aut_portal.Api.FileUploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class portfolios : AppCompatActivity() {

    private lateinit var homeClick: TextView
    private lateinit var jobListingsClick: TextView
    private lateinit var portfoliosClick: TextView


    fun onItemClick() {
        portfoliosClick.setOnClickListener {
            val intent = Intent(this, portfolios::class.java)
            startActivity(intent)
        }

        homeClick.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
        jobListingsClick.setOnClickListener {
            val intent = Intent(this, jobListings::class.java)
            startActivity(intent)
        }
    }

    private lateinit var btnSelectFile: Button
    private lateinit var tvSelectedFile: TextView
    private var selectedFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_portfolios)

        homeClick = findViewById(R.id.link_home)
        jobListingsClick = findViewById(R.id.job_listings)
        portfoliosClick = findViewById(R.id.link_portfolios)
        btnSelectFile = this.findViewById(R.id.btn_select_file)
        tvSelectedFile = findViewById(R.id.tv_selected_file)

        btnSelectFile.setOnClickListener {
            openFileSelector()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onItemClick()
    }
    private val PICK_FILE_REQUEST_CODE = 1

    private fun openFileSelector() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // Accept all file types, or specify (e.g., "image/*")
        }
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                selectedFileUri = uri
                val fileName = getFileName(uri)
                tvSelectedFile.text = fileName
                uploadFile(uri) // Call the upload function
            }
        }
    }
    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String {
        var name = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return name
    }
    private fun uploadFile(uri: Uri) {
        val file = File(getRealPathFromURI(uri))
        val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://192.168.1.106/Final Fall 24-25/") // Replace with your server's base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FileUploadService::class.java)
        val call = service.uploadFile(filePart)

        var uploadedFileUrl: String? = null


        call.enqueue(object : retrofit2.Callback<FileUploadResponse> {
            override fun onResponse(
                call: retrofit2.Call<FileUploadResponse>,
                response: retrofit2.Response<FileUploadResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { uploadResponse ->
                        if (uploadResponse.success) {
                            uploadedFileUrl = uploadResponse.fileUrl
                            tvSelectedFile.text = "Uploaded File: ${file.name}"
                            tvSelectedFile.setTextColor(resources.getColor(R.color.purple_700, null))

                            // Make the TextView clickable
                            tvSelectedFile.setOnClickListener {
                                openFileInBrowser(uploadedFileUrl)
                            }
                        } else {
                            tvSelectedFile.text = "Upload failed"
                        }
                    }
                } else {
                    tvSelectedFile.text = "Upload failed: ${response.code()}"
                }
            }

            override fun onFailure(call: retrofit2.Call<FileUploadResponse>, t: Throwable) {
                tvSelectedFile.text = "Error: ${t.message}"
            }
        })
    }
    private fun openFileInBrowser(fileUrl: String?) {
        if (fileUrl != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl))
            startActivity(intent)
        } else {
            tvSelectedFile.text = "No file URL available"
        }
    }


    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        return cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            it.getString(columnIndex)
        } ?: uri.path ?: ""
    }


}