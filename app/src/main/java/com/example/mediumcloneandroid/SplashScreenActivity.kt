package com.example.mediumcloneandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.mediumcloneandroid.signin_signup.MainActivity
import com.example.mediumcloneandroid.ui.MainUi
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        setStatusBarColor()

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null) {
                startActivity(Intent(this, MainUi::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }, 2000)
    }

    private fun setStatusBarColor() {
        val window: Window = this.window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(this,R.color.black)
    }
}