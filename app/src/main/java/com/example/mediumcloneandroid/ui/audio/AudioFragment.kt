package com.example.mediumcloneandroid.ui.audio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.ui.home.HomeViewModel

class AudioFragment : Fragment() {

    private lateinit var audioViewModel: AudioViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        audioViewModel =
            ViewModelProvider(this).get(AudioViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_audio, container, false)
        val textView: TextView = root.findViewById(R.id.text_audio)
        audioViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}