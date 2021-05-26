package com.example.mediumcloneandroid.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var dbRef: DatabaseReference

    private lateinit var storyList: ArrayList<StoryItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        dbRef = FirebaseDatabase.getInstance().getReference("Stories")
        storyList = arrayListOf()
        initLoader()

        view.recycler_view.layoutManager = LinearLayoutManager(context)
        view.recycler_view.setHasFixedSize(true)

        return view
    }

    private fun initLoader() {
        dbRef.child("AllStories").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                storyList.clear()

                for (storySnapshot in snapshot.children) {
                    val storyItem = storySnapshot.getValue(StoryItem::class.java)
                    storyList.add(storyItem!!)
                }

                view?.recycler_view?.adapter = StoryItemAdapter(storyList)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }

//    @SuppressLint("SimpleDateFormat")
//    private fun generateDummyList(size: Int): List<StoryItem> {
//
//        val list = ArrayList<StoryItem>()
//
//        val currentDate = SimpleDateFormat("MMM dd, yyyy").format(Date())
//        val drawable = "null"
//        val storyTitle = "Some Arbitrary Text"
//        val storyFull = getString(R.string.some_arbitrary_text2)
//        val author = "David"
//
//        for (i in 0 until size) {
//            val item = StoryItem(drawable, storyTitle, author, currentDate, storyFull)
//
//            list += item
//        }
//
//        return list
//    }

}