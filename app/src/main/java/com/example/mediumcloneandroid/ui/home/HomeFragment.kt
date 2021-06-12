package com.example.mediumcloneandroid.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.StoryItemAdapter
import com.example.mediumcloneandroid.data.StoryItem
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var dbRef: DatabaseReference

    private lateinit var storyList: ArrayList<StoryItem>

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerEffect: ShimmerFrameLayout
    private lateinit var refresh: SwipeRefreshLayout

    private lateinit var connMgr: ConnectivityManager

    private var netInfo: NetworkInfo? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Init
        shimmerEffect = view.findViewById(R.id.progress_bar_home)
        refresh = view.findViewById(R.id.swipe_to_refresh)
        recyclerView = view.findViewById(R.id.recycler_view)

        storyList = arrayListOf()
        dbRef = FirebaseDatabase.getInstance().getReference("Stories")

        // Recycler view setup
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        // Network Connectivity
        connMgr = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        netInfo = connMgr.activeNetworkInfo

        // Initiate animations
        shimmerEffect.startShimmerAnimation()

        // Network and views init
        if (netInfo != null && netInfo!!.isConnected) {
            initLoader()
        } else {
            shimmerEffect.stopShimmerAnimation()
            shimmerEffect.isVisible = false
            view.internet_home.isVisible = true
        }

        bindListeners()

        return view
    }

    private fun bindListeners() {

        refresh.setOnRefreshListener {
            refreshData()
        }

    }

    private fun refreshData() {
        dbRef.child("AllStories").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                // Clear list
                storyList.clear()

                // Add data
                for (storySnapshot in snapshot.children) {
                    val storyItem = storySnapshot.getValue(StoryItem::class.java)
                    storyList.add(storyItem!!)
                }

                // Network and views handling
                if (netInfo != null && netInfo!!.isConnected) {
                    recyclerView.adapter?.notifyDataSetChanged()
                    refresh.isRefreshing = false
                } else {
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                    refresh.isRefreshing = false
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }

    private fun initLoader() {
        dbRef.child("AllStories").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                // Clear list
                storyList.clear()

                // Add data
                for (storySnapshot in snapshot.children) {
                    val storyItem = storySnapshot.getValue(StoryItem::class.java)
                    storyList.add(storyItem!!)
                }

                // setup recycler and animation handling
                recyclerView.adapter = StoryItemAdapter(storyList)
                shimmerEffect.stopShimmerAnimation()
                shimmerEffect.isVisible = false
                recyclerView.isVisible = true

                if (refresh.isRefreshing) {
                    refresh.isRefreshing = false
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }

    override fun onResume() {
        super.onResume()
        shimmerEffect.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerEffect.stopShimmerAnimation()
        super.onPause()
    }

}