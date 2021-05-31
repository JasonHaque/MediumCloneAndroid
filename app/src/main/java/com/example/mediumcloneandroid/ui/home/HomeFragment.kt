package com.example.mediumcloneandroid.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.StoryItemAdapter
import com.example.mediumcloneandroid.data.StoryItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var dbRef: DatabaseReference

    private lateinit var storyList: ArrayList<StoryItem>

    private lateinit var progressBar: ProgressBar

    private lateinit var connMgr: ConnectivityManager

    private var netInfo: NetworkInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar = view.findViewById(R.id.progress_bar_home)

        connMgr = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        netInfo = connMgr.activeNetworkInfo

        view.recycler_view.isVisible = false
        progressBar.isVisible = true

        dbRef = FirebaseDatabase.getInstance().getReference("Stories")
        storyList = arrayListOf()

        if (netInfo != null && netInfo!!.isConnected) {
            initLoader()
        } else {
            progressBar.isVisible = false
            view.internet_home.isVisible = true
        }


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

                view!!.recycler_view.adapter = StoryItemAdapter(storyList)
                progressBar.isVisible = false
                view!!.recycler_view.isVisible = true

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }

}