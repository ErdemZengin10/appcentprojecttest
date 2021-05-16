package com.erdemzengin.VideoGamesAppAppcent.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.erdemzengin.VideoGamesAppAppcent.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent= Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
            finish()


        },5000)








    }
}