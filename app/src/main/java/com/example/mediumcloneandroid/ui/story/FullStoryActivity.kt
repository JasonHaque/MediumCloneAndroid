package com.example.mediumcloneandroid.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mediumcloneandroid.R
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

        title = intent.getStringExtra("Title").toString()
        date = intent.getStringExtra("Date").toString()
        author = intent.getStringExtra("Author").toString()
        image = intent.getStringExtra("Image").toString()
        story = intent.getStringExtra("Story").toString()


        if (image == "null") {
            image_full_story.setImageResource(R.drawable.light_tile)
        } else {
            Glide.with(applicationContext).load(image).into(image_full_story)
        }

        title_full_story.text = title
        full_story.text = story
//
//        setSupportActionBar(toolbar)

    }

}