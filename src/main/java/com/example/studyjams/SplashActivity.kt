package com.example.studyjams

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val goToLogin = Runnable {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        applyInsets(findViewById(R.id.splashRoot))

        findViewById<ImageView>(R.id.logo)
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
    }

    override fun onStart() {
        super.onStart()
        handler.postDelayed(goToLogin, 2000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(goToLogin)
    }
}
