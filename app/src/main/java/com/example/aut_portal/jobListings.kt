package com.example.aut_portal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aut_portal.Adapters.AdapterJob
import com.example.aut_portal.Entity.JobEntity
import kotlin.jvm.java
import kotlin.let
import kotlin.run

class jobListings : AppCompatActivity() {

    private lateinit var jobListView: ListView
    private lateinit var portfoliosClick: TextView
    private lateinit var homeClick: TextView
    private lateinit var jobListingsClick: TextView
    private var job: MutableList<JobEntity> = mutableListOf()
    private var originalJobList: MutableList<JobEntity> = mutableListOf()  // Store the original list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_listings)
        enableEdgeToEdge()

        // Initialize views
        jobListView = findViewById(R.id.jobListView)
        portfoliosClick = findViewById(R.id.link_portfolios)
        homeClick = findViewById(R.id.link_home)
        jobListingsClick = findViewById(R.id.job_listings)

        // Set up edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchJobListings()
        searchJobs()
        setupNavigation()
    }

    private fun fetchJobListings() {
        Log.d("JobListingsActivity", "Starting to fetch job listings")

        RetrofitClient.getJobListings(action = 1) { jobEntity ->
            jobEntity?.let {
                job.clear() // Clear existing list
                job.addAll(it) // Add fetched jobs to the list
                originalJobList = job.toMutableList() // Store the original list

                val adapter = AdapterJob(this, job)
                jobListView.adapter = adapter
            } ?: run {
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchJobs() {
        val searchBar = findViewById<EditText>(R.id.SearchBar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString() // Get the current search query
                filterJobs(searchText) // Call the filter function
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Function to filter the jobs based on search text
    private fun filterJobs(query: String) {
        val filteredItems = if (query.isEmpty()) {
            originalJobList // If search query is empty, show the original list
        } else {
            job.filter {
                it.JobL_Position.contains(query, ignoreCase = true) || it.Comp_Name.contains(query, ignoreCase = true)
            }
        }

        // Update the adapter with the filtered jobs
        (jobListView.adapter as AdapterJob).updateData(filteredItems)
    }
    private fun setupNavigation() {
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
        jobListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, JobDescription::class.java)
            intent.putExtra("Comp_Logo", job[position].Comp_Logo)
            intent.putExtra("Comp_Name", job[position].Comp_Name)
            intent.putExtra("JobL_Position", job[position].JobL_Position)
            intent.putExtra("JobL_Info",job[position].JobL_Info)
            intent.putExtra("JobL_Qualification",job[position].JobL_Qualification)

            startActivity(intent)
        }
    }
}


