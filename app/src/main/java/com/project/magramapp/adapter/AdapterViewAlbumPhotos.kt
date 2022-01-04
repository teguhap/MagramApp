package com.project.magramapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.magramapp.R
import com.project.magramapp.dataclass.PhotosData
import com.squareup.picasso.Picasso


class AdapterViewAlbumPhotos(val listPhotos : List<PhotosData>) : RecyclerView.Adapter<AdapterViewAlbumPhotos.ViewHolderView>() {

    inner class ViewHolderView(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_view_album_photos, parent, false)
        return ViewHolderView(view)
    }


    override fun onBindViewHolder(holder: ViewHolderView, position: Int) {
        holder.itemView.apply {
            val ivThumbnail = findViewById<ImageView>(R.id.ivThumbnail)
            val currentItem = listPhotos[position]

            val albumId = currentItem.albumId
            val id = currentItem.id
            val title = currentItem.title
            val url = currentItem.url
            val thumbnailUrl = currentItem.thumbnailUrl


            Picasso.get().load(thumbnailUrl).fit().centerCrop().into(ivThumbnail)

            ivThumbnail.setOnClickListener {
                Toast.makeText(context,"Thumbnail terclick",Toast.LENGTH_SHORT).show()
            }


        }


    }

    override fun getItemCount(): Int {

        return listPhotos.size
    }
}