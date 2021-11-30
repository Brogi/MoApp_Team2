package com.example.myapplication

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.net.URL

class SelectedImgAdapter(private val context : Context, private var list : ArrayList<Uri>) : RecyclerView.Adapter<SelectedImgAdapter.ItemViewHoder>() {
    inner class ItemViewHoder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val selectedImgView = itemView.findViewById<ImageView>(R.id.selectedImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHoder {
        val view = LayoutInflater.from(context).inflate(R.layout.selected_img_item,parent,false)
        return ItemViewHoder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHoder, position: Int) {
        if(list.size == 0)
            return
        // Uri 로딩이 오래 걸림
        // 해당 Uri의 이미지 캐싱 및 불러오기
        Glide.with(context)
            .load(list[position])
            .placeholder(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(holder.selectedImgView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}