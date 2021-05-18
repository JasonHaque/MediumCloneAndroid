package com.example.mediumcloneandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.data.StoryItem
import kotlinx.android.synthetic.main.story_item.view.*

class StoryItemAdapter(private val storyList: List<StoryItem>) : RecyclerView.Adapter<StoryItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.story_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = storyList[position]

        if (currentItem.imageView == "null") {
            holder.imageView.setImageResource(R.drawable.ic_baseline_person_24)
        } else {
            Glide.with(holder.imageView.context)
                .load(currentItem.imageView.toString())
                .into(holder.imageView)
        }

        holder.storyTitle.text = currentItem.storyTitle
        holder.datePosted.text = currentItem.postedDate
        holder.timePosted.text = currentItem.postedTime
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var imageView: ImageView = itemView.writer_profile_picture
        var storyTitle: TextView = itemView.story_title
        var timePosted: TextView = itemView.posted_time
        var datePosted: TextView = itemView.posted_date

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

}