package com.jstark.gitu.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.jstark.gitu.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        delayIntent()
    }

    private fun delayIntent() {
        Handler().postDelayed({
            val moveHome = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(moveHome)
            finish()
        }, 4000)
    }
}