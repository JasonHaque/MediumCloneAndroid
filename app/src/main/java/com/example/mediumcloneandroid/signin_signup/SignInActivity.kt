package com.example.mediumcloneandroid.signin_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mediumcloneandroid.ui.MainUi
import com.example.mediumcloneandroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        bindListeners()
    }

    private fun bindListeners() {

        sign_in_button.setOnClickListener {

            val email = email_field_sign_in.text.toString()
            val password = password_field_sign_in.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill up fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            logInUser(email, password)
        }
    }

    private fun logInUser(email: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainUi::class.java))

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show()
            return@addOnFailureListener
        }
    }
}