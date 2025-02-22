package com.example.aut_portal.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.aut_portal.Entity.PostEntity
import com.example.aut_portal.Entity.PostWithComment
import com.example.aut_portal.R

class homeAdapter(
    private val context: Context, private var postWithCommentsList: List<PostWithComment> // Use mutable list to allow updates
) : ArrayAdapter<PostWithComment>(context, 0, postWithCommentsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.post_list_layout, parent, false)

        val postWithComments = getItem(position)
        val post = postWithComments?.post
        val commentList = postWithComments?.comments

        // Post data handling
        val postInfo = view.findViewById<TextView>(R.id.PostInfo)
        val postPic = view.findViewById<ImageView>(R.id.PostPic)
        val likeIcon = view.findViewById<ImageView>(R.id.likeIcon)
        val commentImageView: ImageView = view.findViewById(R.id.CommentIcon)
        val commentSection = view.findViewById<LinearLayout>(R.id.commentSection)
        val commentInfo = view.findViewById<TextView>(R.id.commentContent)
        val commentName = view.findViewById<TextView>(R.id.profileName)
        val profilePic = view.findViewById<ImageView>(R.id.profilePic)

        postInfo.text = post?.Post_Info ?: "No post info available"

        // Comment data handling
        if (!commentList.isNullOrEmpty()) {
            commentInfo.text = commentList[0].Comment_Info ?: "No comment info available"
            commentName.text = commentList[0].Std_Name ?: "Anonymous"
        } else {
            commentInfo.text = "No comments yet"
            commentName.text = ""
        }

        // Image loading
        val imageUrl = post?.PostPicUrl
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .into(postPic)
        } else {
            Log.e("Glide", "Invalid or null image URL for post ID: ${post?.Post_Id}")
            postPic.setImageResource(R.drawable.university) // Set a default image if URL is invalid
        }

        if(!commentList.isNullOrEmpty()) {
            val profileImageUrl = commentList[0].Std_ProfPic
            if (!profileImageUrl.isNullOrEmpty()) {
                Glide.with(context)
                    .load(profileImageUrl)
                    .into(profilePic)
            }
        }

        // Toggle comment section visibility
        commentImageView.setOnClickListener {
            commentSection.visibility = if (commentSection.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        return view
    }

}


