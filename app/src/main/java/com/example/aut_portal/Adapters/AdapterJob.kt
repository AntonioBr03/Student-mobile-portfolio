package com.example.aut_portal.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.aut_portal.Entity.JobEntity
import com.example.aut_portal.R
import kotlin.toString

class AdapterJob(
private val context: Context, private var jobList: MutableList<JobEntity> // Make jobList mutable
) : ArrayAdapter<JobEntity>(context, 0, jobList) {

    // Function to update the list data
    fun updateData(newJobs: List<JobEntity>) {
        jobList.clear() // Clear existing data
        jobList.addAll(newJobs) // Add new data
        notifyDataSetChanged() // Notify adapter that data has changed
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listlayout, parent, false)

        val job = getItem(position)

        val JobL_Position: TextView = view.findViewById(R.id.jobPosition)
        val jobcompname = view.findViewById<TextView>(R.id.comapnyname)
        val jobL_logo = view.findViewById<ImageView>(R.id.ImageviewJobLogo)
        val imageUrl = job?.Comp_Logo

        JobL_Position.text = job?.JobL_Position
        jobcompname.text = job?.Comp_Name
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .into(jobL_logo)
        } else {
            Log.e("Glide", "Invalid or null image URL")
            jobL_logo.setImageResource(R.drawable.university) // Set a default image if URL is invalid
        }

        return view
    }
}


