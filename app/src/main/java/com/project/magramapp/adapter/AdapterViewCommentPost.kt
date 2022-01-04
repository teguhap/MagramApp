package com.project.magramapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.magramapp.R
import com.project.magramapp.dataclass.CommentData


class AdapterViewCommentPost(val listComment : List<CommentData>) : RecyclerView.Adapter<AdapterViewCommentPost.ViewHolderView>() {

    inner class ViewHolderView(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_view_comment_post, parent, false)
        return ViewHolderView(view)
    }


    override fun onBindViewHolder(holder: ViewHolderView, position: Int) {
        holder.itemView.apply {
        val tvUserComment = findViewById<TextView>(R.id.tvUserComment)
        val tvBodyComment = findViewById<TextView>(R.id.tvBodyComment)

            tvUserComment.text = listComment[position].username
            tvBodyComment.text = listComment[position].body
        }
    }

    override fun getItemCount(): Int {

        return listComment.size
    }
}