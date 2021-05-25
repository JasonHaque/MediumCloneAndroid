package com.example.mediumcloneandroid.ui.my_story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mediumcloneandroid.R

class MyStoryFragment : Fragment() {

    private lateinit var myStoryViewModel: MyStoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myStoryViewModel =
            ViewModelProvider(this).get(MyStoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_stories, container, false)
        val textView: TextView = root.findViewById(R.id.text_interests)
        myStoryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}