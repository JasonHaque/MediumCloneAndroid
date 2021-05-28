package com.example.mediumcloneandroid.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.data.Writer
import kotlinx.android.synthetic.main.writer_item.view.*

class WriterAdapter(private val writerList: ArrayList<Writer>) :
    RecyclerView.Adapter<WriterAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.writer_picture
        var writerName = itemView.writer_name
        var supportButton = itemView.support_writers_button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.writer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWriter = writerList[position]
        var black = true

        Glide.with(holder.writerName.context).load(currentWriter.authorPicture).into(holder.imageView)
        holder.writerName.text = currentWriter.authorName

        holder.supportButton.setOnClickListener {
            black = if (black) {
                holder.supportButton.setBackgroundColor(Color.GREEN)
                Toast.makeText(holder.imageView.context, "Supported ${currentWriter.authorName}", Toast.LENGTH_SHORT).show()
                false
            } else {
                holder.supportButton.setBackgroundColor(Color.BLACK)
                Toast.makeText(holder.imageView.context, "Removed from support", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return writerList.size
    }
}