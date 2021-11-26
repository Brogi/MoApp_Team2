package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplication.model.Map
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StoriesActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var floatingBtn: FloatingActionButton
    lateinit var addStoryBtn: FloatingActionButton
    lateinit var changeColorBtn: FloatingActionButton
    lateinit var backBtn: Button
    lateinit var localTv: TextView
    lateinit var db: AppDatabase
    lateinit var fabOpen: Animation
    lateinit var fabClose: Animation
    var isFabOpen: Boolean = false

    lateinit var dataL : ArrayList<ThumbNailData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)

        initView()
        callDB()

        var intent = intent
        val mapId = intent.getIntExtra("Local", 0)
        val output = db.mapDao().getAllById(mapId)[0]

        intent = Intent(applicationContext, AddStoryActivity::class.java)
        localTv.text = output.mapName

        floatingBtn.setOnClickListener {
            anim()
        }
        addStoryBtn.setOnClickListener {
            anim()
            intent = Intent(applicationContext, AddStoryActivity::class.java)
            intent.putExtra("Local",mapId)
            startActivity(intent)
            finish()
        }
        changeColorBtn.setOnClickListener {
            anim()
            intent = Intent(applicationContext, ChangeColorActivity::class.java)
            intent.putExtra("Local", mapId)
            startActivity(intent)
        }
        backBtn.setOnClickListener {
            intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
            finish()
        }

        var dataGetStory = db.storyDao().getAllById(mapId)

        dataL = ArrayList<ThumbNailData>()
        for(i in dataGetStory.indices){
            var dataGetImage = db.pictureDao().getAllByStoryId(dataGetStory[i].storyId!!)
            dataL.add(ThumbNailData(dataGetStory[i].date,dataGetImage[0].image!!,dataGetStory[i].storyId!!))
        }

        /*dataL = arrayListOf<ThumbNailData>(
            ThumbNailData("2021.01.01", BitmapFactory.decodeResource(applicationContext.resources,R.drawable.pic_a))
        )*/

        val myAdapter = StoriesAdapter(this, dataL)
        recyclerView.adapter = myAdapter

        val gridLayoutManager = GridLayoutManager(applicationContext, 3)

        recyclerView.layoutManager = gridLayoutManager
    }

    private fun anim() {
        if(isFabOpen) {
            floatingBtn.setImageResource(R.drawable.ic_add)
            addStoryBtn.startAnimation(fabClose)
            changeColorBtn.startAnimation(fabClose)
            addStoryBtn.isClickable = false
            changeColorBtn.isClickable = false
            isFabOpen = false
        } else {
            floatingBtn.setImageResource(R.drawable.ic_close)
            addStoryBtn.startAnimation(fabOpen)
            changeColorBtn.startAnimation(fabOpen)
            addStoryBtn.isClickable = true
            changeColorBtn.isClickable = true
            isFabOpen = true
        }
    }

    private fun initView() {
        fabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)

        floatingBtn = findViewById(R.id.as_FloatingBtn)
        addStoryBtn = findViewById(R.id.as_AddStoryBtn)
        changeColorBtn = findViewById(R.id.as_ChangeColorBtn)

        recyclerView = findViewById(R.id.as_RecyclerV)
        backBtn = findViewById(R.id.as_BackBtn)
        localTv = findViewById(R.id.as_LocalTV)
    }

    private fun callDB() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mapDB"
        ).allowMainThreadQueries().build()
    }
}