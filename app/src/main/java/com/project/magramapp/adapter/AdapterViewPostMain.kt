package com.project.magramapp.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.magramapp.DetailPostActivity
import com.project.magramapp.dataclass.ListPostAdapter
import com.project.magramapp.R


class AdapterViewPostMain(val listPostAdapter : List<ListPostAdapter>) : RecyclerView.Adapter<AdapterViewPostMain.ViewHolderView>() {

    inner class ViewHolderView(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_view_post_main, parent, false)
        return ViewHolderView(view)
    }

    override fun onBindViewHolder(holder: ViewHolderView, position: Int) {
        holder.itemView.apply {

            //Initialization
            val cvPost = findViewById<CardView>(R.id.cvPost)
            val tvTitle = findViewById<TextView>(R.id.tvTitlePost)
            val tvBody = findViewById<TextView>(R.id.tvBodyPost)
            val tvUsername = findViewById<TextView>(R.id.tvUserNamePost)
            val tvCompany = findViewById<TextView>(R.id.tvUserCompanyPost)
            val currentItem = listPostAdapter[position]

            tvTitle.text = currentItem.title
            tvBody.text = currentItem.body
            tvUsername.text = currentItem.username
            tvCompany.text = currentItem.company

            cvPost.setOnClickListener {

                Intent(context, DetailPostActivity :: class.java).also {
                    it.putExtra("postId",currentItem.idPost)
                    it.putExtra("userId",currentItem.userId)
                    it.putExtra("title",tvTitle.text.toString())
                    it.putExtra("username",tvUsername.text.toString())
                    it.putExtra("body",tvBody.text.toString())
                    it.putExtra("company",tvCompany.text.toString())
                    startActivity(context,it, Bundle.EMPTY)
                }
            }


        }
    }

    override fun getItemCount(): Int {

        return listPostAdapter.size
    }
}