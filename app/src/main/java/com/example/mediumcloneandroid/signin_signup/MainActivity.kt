package com.example.mediumcloneandroid.signin_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediumcloneandroid.ui.MainUi
import com.example.mediumcloneandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        checkAuthStatus(user)

        bindListeners()
    }

    private fun bindListeners() {

        sign_up_with_email.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        sign_in.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun checkAuthStatus(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainUi::class.java))
        }
    }
}