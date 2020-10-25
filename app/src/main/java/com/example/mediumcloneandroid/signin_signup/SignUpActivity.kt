package com.example.mediumcloneandroid.signin_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mediumcloneandroid.MainUi
import com.example.mediumcloneandroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        bindListeners()

    }

    private fun bindListeners() {

        sign_up_button.setOnClickListener {

            val username = username_field.text.toString()
            val email = email_field.text.toString()
            val password = password_field.text.toString()
            val confirmPassword = confirm_password_field.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Fields are not properly filled up", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signUpUser(email, password, username)
        }
    }

    private fun signUpUser(textEmail: String, textPassword: String, textUsername: String) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(textEmail, textPassword).addOnSuccessListener {
            Toast.makeText(this, "Successfully created User: $textUsername", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainUi::class.java))

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to create User: $textUsername", Toast.LENGTH_SHORT).show()
            return@addOnFailureListener

        }
    }
}