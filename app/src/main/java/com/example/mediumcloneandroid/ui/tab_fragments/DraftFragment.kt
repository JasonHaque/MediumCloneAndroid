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
import android.widget.ProgressBar
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
import kotlinx.android.synthetic.main.fragment_published.view.*

class DraftFragment : Fragment() {

    private lateinit var dbRef: DatabaseReference

    private lateinit var storyList: ArrayList<StoryItem>

    private lateinit var userEmail: String

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ShimmerFrameLayout
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

        recyclerView = view.findViewById(R.id.recycler_view_draft)
        progressBar = view.findViewById(R.id.progress_bar_draft)
        refresh = view.findViewById(R.id.swipe_to_refresh_draft)

        connMgr = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        netInfo = connMgr.activeNetworkInfo

        recyclerView.isVisible = false
        progressBar.startShimmerAnimation()
        progressBar.isVisible = true

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        if (netInfo != null && netInfo!!.isConnected) {
            initLoader()
        } else {
            progressBar.isVisible = false
            progressBar.stopShimmerAnimation()
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

    private fun refreshData() {
        dbRef.child("Stories").child("NotPublished").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                storyList.clear()

                for (storySnapshot in snapshot.children) {
                    val storyItem = storySnapshot.getValue(StoryItem::class.java)
                    storyList.add(storyItem!!)
                }

                recyclerView.adapter?.notifyDataSetChanged()
                refresh.isRefreshing = false

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }

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
                storyList.clear()

                for (storySnapshot in snapshot.children) {
                    val storyItem = storySnapshot.getValue(StoryItem::class.java)
                    storyList.add(storyItem!!)
                }

                recyclerView.adapter = StoryItemAdapter(storyList)
                progressBar.stopShimmerAnimation()
                progressBar.isVisible = false
                recyclerView.isVisible = true

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }
}