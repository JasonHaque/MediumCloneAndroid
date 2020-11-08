package com.example.mediumcloneandroid.signin_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mediumcloneandroid.ui.MainUi
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.data.UserData
import com.google.android.gms.common.FirstPartyScopes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        bindListeners()

    }

    private fun bindListeners() {

        sign_up_button.setOnClickListener {

            val email = email_field_sign_up.text.toString()
            val password = password_field_sign_up.text.toString()
            val confirmPassword = confirm_password_field.text.toString()
            val firstName = first_name.text.toString()
            val lastName = last_name.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Fields are not properly filled up", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signUpUser(email, password, firstName, lastName)
        }
    }

    private fun signUpUser(textEmail: String, textPassword: String, firstName: String, lastName: String) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(textEmail, textPassword).addOnSuccessListener {
            Toast.makeText(this, "Successfully created User", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainUi::class.java))

            val user = UserData(firstName, lastName)
            val ref = FirebaseDatabase.getInstance().reference
            ref.child("Users/$textEmail").setValue(user).addOnSuccessListener {
                Log.i("SignUpActivity", "Data saved" )
            }.addOnFailureListener { Log.i("SignUpActivity", "Failed to save data") }

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to create User", Toast.LENGTH_SHORT).show()
            return@addOnFailureListener

        }
    }


}