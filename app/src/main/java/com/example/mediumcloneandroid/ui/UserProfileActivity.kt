package com.example.mediumcloneandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediumcloneandroid.R
import com.google.firebase.auth.FirebaseAuth

class UserProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        auth = FirebaseAuth.getInstance()

        setProfileCredentials()
    }

    private fun setProfileCredentials() {
        var user = auth.currentUser

    }
}