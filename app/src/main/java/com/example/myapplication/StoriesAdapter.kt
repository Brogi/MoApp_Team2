package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoriesAdapter(private val context : Context, private  val dataList : ArrayList<ThumbNailData>) : RecyclerView.Adapter<StoriesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tnImage = itemView.findViewById<ImageView>(R.id.story_thumbnailView)
        val dateText = itemView.findViewById<TextView>(R.id.story_dateTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stories_item,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tnImage.setImageResource(dataList[position].tnImage)
        holder.dateText.text = dataList[position].date
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}