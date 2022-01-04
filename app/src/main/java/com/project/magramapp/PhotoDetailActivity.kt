package com.project.magramapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.project.magramapp.databinding.ActivityPhotoDetailBinding
import com.squareup.picasso.Picasso
import java.lang.Float.max
import java.lang.Float.min

class PhotoDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")


        Picasso.get().load(url).fit().into(binding.ivPhoto)
        binding.tvPhotoTitle.text = title
    }

}