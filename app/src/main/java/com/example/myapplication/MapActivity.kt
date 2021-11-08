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
            // 수도권
            R.id.ansan,
            R.id.anseong,
            R.id.anyang,
            R.id.bucheon,
            R.id.daebudo,
            R.id.dongducheon,
            R.id.ganghwa,
            R.id.gapyeong,
            R.id.gimpo,
            R.id.goyang,
            R.id.gunpo,
            R.id.guri,
            R.id.gwacheon,
            R.id.gwangju,
            R.id.gwangmyeong,
            R.id.gyodongdo,
            R.id.hanam,
            R.id.hwaseong,
            R.id.icheon,
            R.id.incheon,
            R.id.namyangju,
            R.id.osan,
            R.id.paju,
            R.id.pocheon,
            R.id.pyeongtaek,
            R.id.seongmodo,
            R.id.seongnam,
            R.id.seoul,
            R.id.siheung,
            R.id.suwon,
            R.id.uijeongbu,
            R.id.uiwang,
            R.id.yangju,
            R.id.yangpyeong,
            R.id.yeoju,
            R.id.yeoncheon,
            R.id.yeongjongdo,
            R.id.yongin,
            // 전북
            R.id.buan,
            R.id.gimje,
            R.id.gochang,
            R.id.gunsan,
            R.id.iksan,
            R.id.imsil,
            R.id.jangsu,
            R.id.jeongeup,
            R.id.jeonju,
            R.id.jinan,
            R.id.muju,
            R.id.namwon,
            R.id.sunchang,
            R.id.wanju,
            // 충청도
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
        val mapImage = arrayOfNulls<ImageView>(82) // 수도권 39, 전북 14, 충청도 29

        for (i in mapId.indices) {
            mapImage[i] = findViewById<ImageView>(mapId[i])
            mapImage[i]!!.setOnClickListener {
                mapImage[i]!!.setColorFilter(Color.parseColor("#9CD7FF"))
            }
        }
    }
}