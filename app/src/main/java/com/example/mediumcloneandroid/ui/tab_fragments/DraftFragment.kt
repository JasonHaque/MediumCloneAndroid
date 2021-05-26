package com.example.mediumcloneandroid.ui.tab_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.StoryItemAdapter
import com.example.mediumcloneandroid.data.StoryItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_draft.view.*

class DraftFragment : Fragment() {

    private lateinit var dbRef: DatabaseReference

    private lateinit var storyList: ArrayList<StoryItem>

    private lateinit var userEmail: String


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

        view.recycler_view_draft.layoutManager = LinearLayoutManager(context)
        view.recycler_view_draft.setHasFixedSize(true)
        initLoader()

        return view
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

                view?.recycler_view_draft?.adapter = StoryItemAdapter(storyList)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }
}