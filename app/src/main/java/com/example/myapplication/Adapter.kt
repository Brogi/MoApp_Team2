package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

class Adapter(private val models: ArrayList<Model>, private val context: Context) : PagerAdapter() {

    override fun getCount(): Int {
        return models.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.item, container, false)

        val imageView: ImageView = view.findViewById(R.id.image)
        val date: TextView = view.findViewById(R.id.date)
        val hashtag: TextView = view.findViewById(R.id.hashtag)

        val uriPathHelper = URIPathHelper()
        val filePath = uriPathHelper.getPath(context, models[position].image.toUri())

        Glide.with(context)
            .load(filePath)
            .placeholder(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_background)
            .into(imageView)

        date.setText(models[position].date)
        hashtag.setText(models[position].hashtag)

        imageView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("param", filePath)
            context.startActivity(intent)
            // finish();
        }
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

}