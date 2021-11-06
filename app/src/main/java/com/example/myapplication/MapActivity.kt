package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val entireMap = findViewById<ImageView>(R.id.entire_map)
        val mapId = arrayOf(
            R.id.asan,
            R.id.boeun,
            R.id.boryeong,
            R.id.buyeo,
            R.id.cheonan,
            R.id.cheongju,
            R.id.cheongyang,
            R.id.chungju,
            R.id.daejeon,
            R.id.dangjin,
            R.id.danyang,
            R.id.eumseong,
            R.id.geumsan,
            R.id.goesan,
            R.id.gongju,
            R.id.gyeryong,
            R.id.hongseong,
            R.id.jecheon,
            R.id.jeungpyeong,
            R.id.jincheon,
            R.id.nonsan,
            R.id.okcheon,
            R.id.sejong,
            R.id.seocheon,
            R.id.seosan,
            R.id.taean,
            R.id.taean_below,
            R.id.yeongdong,
            R.id.yesan
        )
        val mapImage = arrayOfNulls<ImageView>(29)

        for (i in mapId.indices) {
            mapImage[i] = findViewById<ImageView>(mapId[i])
            mapImage[i]!!.setOnClickListener {
                mapImage[i]!!.setColorFilter(Color.parseColor("#9CD7FF"))
            }
        }
    }
}