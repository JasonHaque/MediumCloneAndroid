package com.example.mediumcloneandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.databinding.ActivityFullStoryBinding
import kotlinx.android.synthetic.main.activity_full_story.*

class FullStoryActivity : AppCompatActivity() {

    private lateinit var title: String
    private lateinit var date: String
    private lateinit var author: String
    private lateinit var image: String
    private lateinit var story: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_story)

        val toolbar = toolbar_story


        title = intent.getStringExtra("Title").toString()
        date = intent.getStringExtra("Date").toString()
        author = intent.getStringExtra("Author").toString()
        image = intent.getStringExtra("Image").toString()
        story = intent.getStringExtra("Story").toString()

        toolbar.title = title
        full_story.text = story

        setSupportActionBar(toolbar)

    }

}