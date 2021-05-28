package com.example.mediumcloneandroid.ui.my_story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.SectionPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyStoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_my_stories, container, false)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        val adapter = SectionPageAdapter(requireActivity().supportFragmentManager, lifecycle)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position) {
                0 -> {tab.text = "Published"}
                1 -> {tab.text = "Draft"}
            }
        }.attach()

        return view
    }
}