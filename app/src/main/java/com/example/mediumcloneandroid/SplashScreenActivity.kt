package com.example.mediumcloneandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mediumcloneandroid.signin_signup.MainActivity
import com.example.mediumcloneandroid.ui.MainUi
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null) {
                startActivity(Intent(this, MainUi::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2000)
    }

}