package com.example.mediumcloneandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mediumcloneandroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.nav_header_main.*

class UserProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        auth = FirebaseAuth.getInstance()

        setProfileCredentials()
    }

    private fun setProfileCredentials() {
        val user = auth.currentUser

        val name = user?.displayName.toString()
        if (name == "") {
            user_name.text = getString(R.string.profile_name)
        } else {user_name.text = name}

    }
}