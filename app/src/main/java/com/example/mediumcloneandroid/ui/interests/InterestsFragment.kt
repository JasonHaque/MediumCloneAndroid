package com.example.mediumcloneandroid.ui.interests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mediumcloneandroid.R

class InterestsFragment : Fragment() {

    private lateinit var interestsViewModel: InterestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        interestsViewModel =
            ViewModelProvider(this).get(InterestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_interests, container, false)
        val textView: TextView = root.findViewById(R.id.text_interests)
        interestsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}