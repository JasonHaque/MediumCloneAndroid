package com.example.mediumcloneandroid.ui.story

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.data.StoryItem
import com.example.mediumcloneandroid.ui.MainUi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_create.*
import java.text.SimpleDateFormat
import java.util.*

class CreateFragment : Fragment() {

    private lateinit var buttonDone: ImageView
    private lateinit var buttonPublish: TextView

    private lateinit var story: String
    private lateinit var author: String
    private lateinit var storyTitle: String
    private lateinit var currentDate: String
    private lateinit var image: String

    private lateinit var email: String

    private lateinit var connMgr: ConnectivityManager
    private var netInfo: NetworkInfo? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        connMgr = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        netInfo = connMgr.activeNetworkInfo

        buttonDone = view.findViewById(R.id.done)
        buttonPublish = view.findViewById(R.id.publish)

        bindListeners()

        return view
    }

    private fun bindListeners() {
        buttonDone.setOnClickListener {
            if (title_story_edit_text.text.toString() != "" || story_edit_text.text.toString() != "") {

                if (netInfo != null && netInfo!!.isConnected) {
                    getCredentials()
                    postStory("NotPublished")
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {
                startActivity(Intent(activity, MainUi::class.java))
            }
        }

        buttonPublish.setOnClickListener {
            if (title_story_edit_text.text.toString() != "" || story_edit_text.text.toString() != "") {

                if (netInfo != null && netInfo!!.isConnected) {
                    getCredentials()
                    postStoryInPublic()
                    postStory("Published")
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {
                Toast.makeText(context, "Fill in your Story and Title", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun postStoryInPublic() {
        val post = StoryItem(image, currentDate, author, story, storyTitle)

        val key = storyTitle
        key.replace("\\S".toRegex(), "_")

        val db = FirebaseDatabase.getInstance().getReference("Stories")
            .child("AllStories")

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.hasChild(key)) {
                    db.child(key).setValue(post).addOnSuccessListener {
                        Toast.makeText(context, "Story Published", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Could not write data", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }

    private fun postStory(place: String) {
        val post = StoryItem(image, currentDate, author, story, storyTitle)

        val key = storyTitle
        key.replace("\\s".toRegex() , "_")

        val db = FirebaseDatabase.getInstance().getReference("users")
            .child(email)
            .child("Stories")
            .child(place)

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.hasChild(key)) {
                    db.child(key).setValue(post).addOnSuccessListener {
                        if (place == "NotPublished") {
                            Toast.makeText(context, "Story saved on Draft", Toast.LENGTH_SHORT).show()
                        }
                        startActivity(Intent(activity, MainUi::class.java))
                    }.addOnFailureListener {
                        Toast.makeText(context, "Could not write data", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Title is already taken", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(context.toString(), "Something went wrong")
            }

        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCredentials() {
        storyTitle = title_story_edit_text.text.toString()
        story = story_edit_text.text.toString()
        author = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        currentDate = SimpleDateFormat("MMM dd, yyyy").format(Date())
        image = "null"

        val textEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
        getUserEmail(textEmail)
    }

    private fun getUserEmail(textEmail: String) {
        email = textEmail
        if (textEmail.contains("@")) {
            val mailSplitter = textEmail.split("@")
            val emailName = mailSplitter[0]
            val emailValue = mailSplitter[1]
            val emailValue2 = emailValue.replace(".", "_")
            email = emailName + "_" + emailValue2
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()
    }

    override fun onPause() {
        showActionBar()
        super.onPause()
    }

    override fun onDestroy() {
        showActionBar()
        super.onDestroy()
    }

    override fun onDestroyView() {
        showActionBar()
        super.onDestroyView()
    }

    override fun onStop() {
        showActionBar()
        super.onStop()
    }

    override fun onResume() {
        hideActionBar()
        super.onResume()
    }

    private fun hideActionBar() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    private fun showActionBar() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}