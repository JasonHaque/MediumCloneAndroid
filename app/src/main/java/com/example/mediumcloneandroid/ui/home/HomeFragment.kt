package com.example.mediumcloneandroid.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.StoryItemAdapter
import com.example.mediumcloneandroid.data.StoryItem
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val adapter = StoryItemAdapter(generateDummyList(100))
        root.recycler_view.adapter = adapter
        root.recycler_view.layoutManager = LinearLayoutManager(context)
        root.recycler_view.setHasFixedSize(true)

        return root
    }

    @SuppressLint("SimpleDateFormat")
    private fun generateDummyList(size: Int): List<StoryItem> {

        val list = ArrayList<StoryItem>()

        val sdf = SimpleDateFormat("MMM dd, yyyy")
        val currentTime = sdf.format(Date())

        for (i in 0 until size) {
            val drawable = "null"
            val item = StoryItem(drawable, "Text $i", "5 minutes ago", currentTime)

            list += item
        }

        return list
    }

}