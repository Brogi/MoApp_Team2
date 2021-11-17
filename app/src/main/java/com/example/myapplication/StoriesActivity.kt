package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StoriesActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var addStoryBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)

        recyclerView = findViewById(R.id.as_RecyclerV)
        addStoryBtn = findViewById(R.id.as_AddStoryBtn)

        addStoryBtn.setOnClickListener {
            var intent = Intent(applicationContext,AddStoryActivity::class.java)
            startActivity(intent)
        }

        var dataL = arrayListOf<ThumbNailData>(
            ThumbNailData("2021.01.01",R.drawable.pic_a),
            ThumbNailData("2021.02.02",R.drawable.pic_b),
            ThumbNailData("2021.03.03",R.drawable.pic_c),
            ThumbNailData("2021.04.04",R.drawable.pic_d),
            ThumbNailData("2021.05.05",R.drawable.pic_e),
            ThumbNailData("2021.06.06",R.drawable.pic_f),
            ThumbNailData("2021.07.07",R.drawable.pic_g),
            ThumbNailData("2021.08.08",R.drawable.pic_a),
            ThumbNailData("2021.01.01",R.drawable.pic_a),
            ThumbNailData("2021.02.02",R.drawable.pic_b),
            ThumbNailData("2021.03.03",R.drawable.pic_c),
            ThumbNailData("2021.04.04",R.drawable.pic_d),
            ThumbNailData("2021.05.05",R.drawable.pic_e),
            ThumbNailData("2021.06.06",R.drawable.pic_f),
            ThumbNailData("2021.07.07",R.drawable.pic_g),
            ThumbNailData("2021.08.08",R.drawable.pic_a)
        )

        val myAdapter = StoriesAdapter(this,dataL)
        recyclerView.adapter = myAdapter

        val gridLayoutManager = GridLayoutManager(applicationContext,3)

        recyclerView.layoutManager = gridLayoutManager
    }
}