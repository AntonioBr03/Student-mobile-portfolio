package com.example.aut_portal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aut_portal.Adapters.homeAdapter
import com.example.aut_portal.Entity.PostWithComment

class HomePage : AppCompatActivity() {

    private lateinit var portfoliosClick: TextView
    private lateinit var homeClick: TextView
    private lateinit var jobListingsClick: TextView
    private lateinit var homeListview: ListView
    private lateinit var ImageUNi: ImageView
    private var Std_ID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        enableEdgeToEdge()

        portfoliosClick = findViewById(R.id.link_portfolios)
        homeClick = findViewById(R.id.link_home)
        jobListingsClick = findViewById(R.id.job_listings)
        homeListview = findViewById(R.id.HomeListview)
        ImageUNi=findViewById(R.id.ImageUni)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onItemClick()
        fetchPosts()
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
    private fun fetchPosts() {
        RetrofitClient.fetchPosts(action = 2) { PostEntity ->
            PostEntity?.let { posts ->
                RetrofitClient.getComment(action = 4) { CommentEntity ->
                    CommentEntity?.let { comments ->
                        val postWithCommentsList = posts.map { post ->
                            val postComments = comments.filter { it.Post_Id == post.Post_Id }
                            PostWithComment(post, postComments)
                        }
                        val adapter = homeAdapter(this, postWithCommentsList)
                        homeListview.adapter = adapter
                    } ?: run {
                        Toast.makeText(this, "Failed to load comments", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                Toast.makeText(this, "Failed to load posts", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
