package com.example.mediumcloneandroid.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.ui.home.HomeFragment

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}