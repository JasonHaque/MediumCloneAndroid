package com.example.mediumcloneandroid.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mediumcloneandroid.ui.tab_fragments.DraftFragment
import com.example.mediumcloneandroid.ui.tab_fragments.PublishedFragment

class SectionPageAdapter(fm: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {PublishedFragment()}
            1 -> {DraftFragment()}
            else -> {Fragment()}
        }
    }
}