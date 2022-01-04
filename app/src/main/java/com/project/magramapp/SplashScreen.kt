package com.project.magramapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.statusBarColor = getColor(R.color.black)
        //Handler SplashScreen
        Handler().postDelayed({ Intent(this,MainActivity :: class.java).also {
            startActivity(it)
            finish()
        } },2000L)

    }
}