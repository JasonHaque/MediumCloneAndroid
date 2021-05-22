package com.example.mediumcloneandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.databinding.ActivityFullStoryBinding
import kotlinx.android.synthetic.main.activity_full_story.*

class FullStoryActivity : AppCompatActivity() {

    private lateinit var title: String
    private lateinit var date: String
    private lateinit var time: String
    private lateinit var image: String
    private lateinit var story: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_story)

        title = intent.getStringExtra("Title").toString()
        date = intent.getStringExtra("Date").toString()
        time = intent.getStringExtra("Time").toString()
        image = intent.getStringExtra("Image").toString()
        story = intent.getStringExtra("Story").toString()

        title_story.text = title
        full_story.text = story

    }

}