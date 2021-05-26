package com.example.mediumcloneandroid.ui.my_story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.SectionPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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

        val tabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = root.findViewById<ViewPager2>(R.id.viewPager)

        val adapter = SectionPageAdapter(requireActivity().supportFragmentManager, lifecycle)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position) {
                0 -> {tab.text = "Published Fragment"}
                1 -> {tab.text = "Draft Fragment"}
            }
        }.attach()

        return root
    }
}