package com.example.mediumcloneandroid.ui.story

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.ui.MainUi

class CreateFragment : Fragment() {

    private lateinit var createViewModel: CreateViewModel

    private lateinit var buttonDone: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createViewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        val view: View? = inflater.inflate(R.layout.fragment_create, container, false)

        buttonDone = view!!.findViewById(R.id.done)

        bindListeners()

        return view
    }

    private fun bindListeners() {
        buttonDone.setOnClickListener {
            startActivity(Intent(activity, MainUi::class.java))
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