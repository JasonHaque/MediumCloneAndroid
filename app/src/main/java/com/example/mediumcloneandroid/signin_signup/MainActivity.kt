package com.example.mediumcloneandroid.signin_signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.data.UserData
import com.example.mediumcloneandroid.ui.MainUi
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 690
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var binding: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //Facebook
        FacebookSdk.sdkInitialize(this)
        binding = findViewById(R.id.sign_up_with_facebook)
        facebookAuth()

        // Firebase Auth instance
        mAuth = FirebaseAuth.getInstance()

        bindListeners()

    }

    private fun bindListeners() {

        sign_in_with_google.setOnClickListener {
            signIn()
        }

        sign_up_with_email.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        sign_in.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun signIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("MainActivity", "Google sign in failed", e)
                    // ...
                }
            } else {
                Log.w("MainActivity", exception.toString())
            }
        }

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("MainActivity", "signInWithCredential:success")
                    startActivity(Intent(this, MainUi::class.java))
                    finish()
                    val mUser = mAuth.currentUser
                    val userEmail = mUser?.email.toString()
                    val name = mUser?.displayName.toString().split(" ")
                    val firstName = name[0]
                    val lastName = name[1]
                    dataQuery(firstName, lastName, userEmail)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                }

                // ...
            }
    }

    private fun facebookAuth() {
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()

        binding.sign_up_with_facebook.setReadPermissions("email", "public_profile")
        binding.sign_up_with_facebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Log.d("MainActivity", "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("MainActivity", "signInWithCredential:success")
                    val mUser = mAuth.currentUser
                    val userEmail = mUser?.email.toString()
                    val name = mUser?.displayName.toString().split(" ")
                    val firstName = name[0]
                    val lastName = name[1]
                    dataQuery(firstName, lastName, userEmail)
                    updateUI(mUser)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }

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

        ref.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(email)) {
                    return
                } else {
                    ref.child(email).push().setValue(user).addOnSuccessListener {
                        Log.i("MainActivity", "Data saved")
                    }.addOnFailureListener { Log.i("MainActivity", "Failed to save data") }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("MainActivity", "Failed to retrieve data from firebase")
            }
        })
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainUi::class.java))
        } else {
            Toast.makeText(this, "Please sign in to continue", Toast.LENGTH_SHORT).show()
        }
    }

}