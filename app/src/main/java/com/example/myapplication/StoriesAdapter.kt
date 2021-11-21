package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class StoriesAdapter(private val context : Context, private  val dataList : ArrayList<ThumbNailData>) : RecyclerView.Adapter<StoriesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tnImage = itemView.findViewById<ImageView>(R.id.story_thumbnailView)
        val dateText = itemView.findViewById<TextView>(R.id.story_dateTV)
        val layout = itemView.findViewById<LinearLayout>(R.id.story_item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stories_item,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tnImage.setImageResource(dataList[position].tnImage)
        holder.dateText.text = dataList[position].date
        holder.layout.setOnClickListener {
            // To be removed
            //Toast.makeText(context,"Hello world!",Toast.LENGTH_SHORT).show()
            var intent = Intent(context,ImageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}