package com.example.mediumcloneandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.signin_signup.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.profile_photo
import kotlinx.android.synthetic.main.activity_user_profile.sign_out
import kotlinx.android.synthetic.main.activity_user_profile.user_name
import kotlinx.android.synthetic.main.nav_header_main.*

class UserProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        auth = FirebaseAuth.getInstance()

        setProfileCredentials()
        bindListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setProfileCredentials() {
        val user = auth.currentUser
        val name = user?.displayName.toString()
        val email = user?.email.toString()

        if (name == "") {
            user_name.text = getString(R.string.profile_name)
        } else {user_name.text = "Profile Name: $name"
        }

        email_field.text = "Email: $email"

        Glide.with(this).load(user?.photoUrl).circleCrop().into(profile_photo)

    }

    private fun bindListeners() {

        sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut().also {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}