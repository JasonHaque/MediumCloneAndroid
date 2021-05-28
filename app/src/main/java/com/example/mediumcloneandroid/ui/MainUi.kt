package com.example.mediumcloneandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.signin_signup.MainActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainUi : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_main)


        mAuth = FirebaseAuth.getInstance()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_my_stories, R.id.nav_create
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        bindListeners()
        setProfileCredentials()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun bindListeners() {
        val navHeader = navView.getHeaderView(0)
        navHeader.setOnClickListener {
            drawerLayout.closeDrawers()
            startActivity(Intent(this, UserProfileActivity::class.java))
        }

    }

    private fun signOut() {
        Toast.makeText(applicationContext, "Signing Out", Toast.LENGTH_SHORT).show()

        mAuth.signOut().also {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setProfileCredentials() {
        val user = mAuth.currentUser
        val name = user?.displayName.toString()
        val navHeaderView: View = navView.getHeaderView(0)
        val mProfileImageView: ImageView = navHeaderView.findViewById(R.id.display_picture_header)
        val mProfileNameTextView: TextView = navHeaderView.findViewById(R.id.profile_name_header)

        if (name == "") {
            mProfileNameTextView.text = getString(R.string.profile_name)
        } else {mProfileNameTextView.text = name}

        Glide.with(this).load(user?.photoUrl).circleCrop().into(mProfileImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.support_writers) {
            startActivity(Intent(this, SupportActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}