package com.example.mediumcloneandroid.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.data.StoryItem
import com.example.mediumcloneandroid.ui.FullStoryActivity
import kotlinx.android.synthetic.main.story_item.view.*

class StoryItemAdapter(private val storyList: List<StoryItem>) : RecyclerView.Adapter<StoryItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.story_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = storyList[position]
        val context = holder.imageView.context

        if (currentItem.Image == "null") {
            holder.imageView.setImageResource(R.drawable.ic_baseline_person_24)
        } else {
            Glide.with(context).load(currentItem.Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView)
        }

        val author = currentItem.StoryAuthor

        holder.storyTitle.text = currentItem.StoryTitle
        holder.datePosted.text = currentItem.PostedDate
        holder.storyAuthor.text = "by $author"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullStoryActivity::class.java)
            intent.putExtra("Title", currentItem.StoryTitle)
            intent.putExtra("Date", currentItem.PostedDate)
            intent.putExtra("Author", currentItem.StoryAuthor)
            intent.putExtra("Image", currentItem.Image)
            intent.putExtra("Story", currentItem.StoryFull)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = storyList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.writer_profile_picture
        var storyTitle: TextView = itemView.story_title
        var storyAuthor: TextView = itemView.author_name
        var datePosted: TextView = itemView.posted_date
    }

}