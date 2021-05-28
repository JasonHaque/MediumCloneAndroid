package com.example.mediumcloneandroid.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.WriterAdapter
import com.example.mediumcloneandroid.data.Writer
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.writer_item.*

class SupportActivity : AppCompatActivity() {

    private var black: Boolean = true

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)


        toolbar_writer.title = "Support Writers"
        setSupportActionBar(toolbar_writer)

        val listview = writer_list

        val authorList: ArrayList<Writer> = ArrayList()

        authorList.add(Writer("Ernest Hemingway", "https://www.biography.com/.image/t_share/MTIwNjA4NjMzODM5NjUwMzE2/ernest-hemingway-9334498-1-402.jpg"))

        listview.layoutManager = LinearLayoutManager(applicationContext)
        listview.setHasFixedSize(true)
        listview.adapter = WriterAdapter(authorList)



    }

}