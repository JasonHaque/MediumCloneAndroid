package com.example.mediumcloneandroid.ui.tab_fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.StoryItemAdapter
import com.example.mediumcloneandroid.data.StoryItem
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_draft.view.*

class DraftFragment : Fragment() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var storyList: ArrayList<StoryItem>
    private lateinit var userEmail: String

    private lateinit var recyclerView: RecyclerView

    private lateinit var shimmerEffect: ShimmerFrameLayout
    private lateinit var refresh: SwipeRefreshLayout

    private lateinit var connMgr: ConnectivityManager
    private var netInfo: NetworkInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserEmail()
        dbRef = FirebaseDatabase.getInstance().getReference("users").child(userEmail)
        storyList = arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_draft, container, false)

        // Store in global variables
        recyclerView = view.findViewById(R.id.recycler_view_draft)
        shimmerEffect = view.findViewById(R.id.progress_bar_draft)
        refresh = view.findViewById(R.id.swipe_to_refresh_draft)

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
            view.internet_draft.isVisible = true
        }

        bindListeners()

        return view
    }

    private fun bindListeners() {

        refresh.setOnRefreshListener {
            refreshData()
        }

    }

//    private fun refreshData() {
//        dbRef.child("Stories").child("Published").addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                // Clear previously loaded list
//                storyList.clear()
//
//                // Add new list
//                for (storySnapshot in snapshot.children) {
//                    val storyItem = storySnapshot.getValue(StoryItem::class.java)
//                    storyList.add(storyItem!!)
//                }
//
//                // Setup recycler view
//                recyclerView.adapter?.notifyDataSetChanged()
//
//                if (netInfo != null && netInfo!!.isConnected) {
//
//                }
//
//                refresh.isRefreshing = false
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.i(context.toString(), "Something went wrong")
//            }
//        })
//    }

    private fun refreshData() {
        // Initiate animations
        shimmerEffect.startShimmerAnimation()

        // Network and views handling
        if (netInfo != null && netInfo!!.isConnected) {
            initLoader()
        } else {
            shimmerEffect.stopShimmerAnimation()
            shimmerEffect.isVisible = false
            refresh.isRefreshing = false
            view?.internet_draft?.isVisible = true
        }
    }

    // Initiate email for db
    private fun getUserEmail() {
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            userEmail = email
        }
        if (email!!.contains("@")) {
            val mailSplitter = email.split("@")
            val emailName = mailSplitter[0]
            val emailValue = mailSplitter[1]
            val emailValue2 = emailValue.replace(".", "_")
            userEmail = emailName + "_" + emailValue2
        }
    }

    private fun initLoader() {
        dbRef.child("Stories").child("NotPublished").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                // Clear list
                storyList.clear()

                // Add new data
                for (storySnapshot in snapshot.children) {
                    val storyItem = storySnapshot.getValue(StoryItem::class.java)
                    storyList.add(storyItem!!)
                }

                // Setup and animation handling
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