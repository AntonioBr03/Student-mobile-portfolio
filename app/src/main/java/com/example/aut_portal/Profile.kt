package com.example.aut_portal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.aut_portal.Api.UserResponse

class Profile : AppCompatActivity() {
    private var Std_ID: Int? = null
    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var userMajorTextView: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var idTextView: TextView
    private lateinit var portfoliosClick: TextView
    private lateinit var homeClick: TextView
    private lateinit var jobListingsClick: TextView
    private lateinit var ImageUNi: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        enableEdgeToEdge()

        // Initialize views
        userNameTextView = findViewById(R.id.textView5)
        userEmailTextView = findViewById(R.id.std_emailAddress)
        userMajorTextView = findViewById(R.id.std_yearOfEnrollment)
        profileImageView = findViewById(R.id.ProfilePic)
        idTextView = findViewById(R.id.textView3)
        portfoliosClick = findViewById(R.id.link_portfolios)
        homeClick = findViewById(R.id.link_home)
        jobListingsClick = findViewById(R.id.job_listings)
        ImageUNi=findViewById(R.id.ImageUni)
        // Get Std_ID from Intent
        Std_ID = intent.getIntExtra("Std_ID", 0)
        Log.d("ProfileActivity", "Received Std_ID: $Std_ID")

        if (Std_ID != null && Std_ID != -1) {
            fetchProfile(Std_ID!!)
        } else {
            Log.e("ProfileActivity", "Invalid or missing Std_ID")
            Toast.makeText(this, "Invalid User ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchProfile(Std_ID: Int) {
        RetrofitClient.fetchProfile(Std_ID) { profileResponse ->
            if (profileResponse != null) {
                Toast.makeText(this, profileResponse.message, Toast.LENGTH_SHORT).show()

                val user = profileResponse.data
                if (user != null) {
                    // Update UI with user details
                    userNameTextView.text = "Major of Student: ${user.Std_Name}"
                    userEmailTextView.text = user.Std_email
                    userMajorTextView.text = user.Std_Major
                    Glide.with(this).load(user.Std_ProfPic).into(profileImageView)
                } else {
                    Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun onItemClick() {
        Std_ID = intent.getIntExtra("Std_ID", 0)
        portfoliosClick.setOnClickListener {
            val intent = Intent(this, portfolios::class.java).apply{
                putExtra("Std_ID", Std_ID)
            }
            startActivity(intent)
        }

        homeClick.setOnClickListener {
            val intent = Intent(this, HomePage::class.java).apply{
                putExtra("Std_ID", Std_ID)
            }
            startActivity(intent)
        }

        jobListingsClick.setOnClickListener {
            val intent = Intent(this, jobListings::class.java).apply{
                putExtra("Std_ID", Std_ID)
            }
            startActivity(intent)
        }
        ImageUNi.setOnClickListener {
            val intent = Intent(this, Profile::class.java).apply{
                putExtra("Std_ID", Std_ID)
            }
            startActivity(intent)
        }
    }

}
