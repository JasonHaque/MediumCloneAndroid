package com.example.mediumcloneandroid.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediumcloneandroid.R

class CreateFragment : Fragment() {

    private lateinit var createViewModel: CreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideActionBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        val view: View = inflater.inflate(R.layout.fragment_create, container, false)

        return view
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