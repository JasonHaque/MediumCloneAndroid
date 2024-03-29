package com.example.mediumcloneandroid.signin_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mediumcloneandroid.ui.MainUi
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() { 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        bindListeners()
    }

    // On button clicks
    private fun bindListeners() {

        sign_up_button.setOnClickListener {

            val email = email_field_sign_up.text.toString()
            val firstName = first_name.text.toString()
            val lastName = last_name.text.toString()
            val password = password_field_sign_up.text.toString()
            val confirmPassword = confirm_password_field.text.toString()

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
            dataQuery(firstName, lastName, textEmail)

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to create User", Toast.LENGTH_SHORT).show()
            return@addOnFailureListener

        }
    }

    // Add data to firebase
    private fun dataQuery(firstName: String, lastName: String, textEmail: String) {
        var email = textEmail
        if (textEmail.contains("@")) {
            val mailSplitter = textEmail.split("@")
            val emailName = mailSplitter[0]
            val emailValue = mailSplitter[1]
            val emailValue2 = emailValue.replace(".", "_")
            email = emailName + "_" + emailValue2
        }

        val user = UserData(firstName, lastName)
        val ref = FirebaseDatabase.getInstance().reference.child("users")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(email)) {
                    return
                } else {
                    ref.child(email).setValue(user).addOnSuccessListener {
                        Log.i("MainActivity", "Data saved")
                    }.addOnFailureListener { Log.i("MainActivity", "Failed to save data") }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("MainActivity", "Failed to retrieve data from firebase")
            }
        })
    }

}