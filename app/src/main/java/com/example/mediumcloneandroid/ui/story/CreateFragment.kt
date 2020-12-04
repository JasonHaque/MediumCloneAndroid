package com.example.mediumcloneandroid.ui.story

import android.os.Bundle
import android.view.*
import android.view.View.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediumcloneandroid.R

class CreateFragment : Fragment() {

    private lateinit var createViewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        return inflater.inflate(R.layout.activity_create, container, false)
    }

}