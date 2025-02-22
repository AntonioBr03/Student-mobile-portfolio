package com.example.aut_portal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class JobDescription : AppCompatActivity() {

    private lateinit var link_home: TextView
    private lateinit var link_portfolios: TextView
    private lateinit var job_listings: TextView

    private lateinit var CompLogoImg: ImageView
    private lateinit var TxtCompName: TextView
    private lateinit var JobPosition: TextView
    private lateinit var JobInfo: TextView
    private lateinit var JobQuali: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_job_description)

        CompLogoImg=findViewById(R.id.imgLogo)
        TxtCompName=findViewById(R.id.TxtCompName)
        JobPosition=findViewById(R.id.JobPosition)
        JobInfo=findViewById(R.id.JobInfo)
        JobQuali=findViewById(R.id.JobQualification)
        link_portfolios = findViewById(R.id.link_portfolios)
        link_home = findViewById(R.id.link_home)
        job_listings = findViewById(R.id.job_listings)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ImportedCompName=intent.getStringExtra("Comp_Name")
        val ImportedCompLogo=intent.getStringExtra("Comp_Logo")
        val ImportedJobPos=intent.getStringExtra("JobL_Position")
        val ImportedJobInfo=intent.getStringExtra("JobL_Info")
        val ImportedJobQuali=intent.getStringExtra("JobL_Qualification")

        TxtCompName.text=ImportedCompName
        JobPosition.text=ImportedJobPos
        JobInfo.text=ImportedJobInfo
        JobQuali.text=ImportedJobQuali
        if (!ImportedCompLogo.isNullOrEmpty()) {
            Glide.with(this)
                .load(ImportedCompLogo)
                .into(CompLogoImg)
        } else {
            CompLogoImg.setImageResource(R.drawable.university) // Fallback image if no URL is provided
        }
        onItemClick()
    }
    private fun onItemClick() {
        link_portfolios.setOnClickListener {
            val intent = Intent(this, portfolios::class.java)
            startActivity(intent)
        }

        link_home.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        job_listings.setOnClickListener {
            val intent = Intent(this, jobListings::class.java)
            startActivity(intent)
        }
    }
}