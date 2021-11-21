package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplication.model.Map

class StoriesActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
<<<<<<< Updated upstream
    lateinit var addStoryBtn : Button
    lateinit var localTv : TextView
=======
    lateinit var addStoryBtn: Button
    lateinit var changeColorBtn: Button
    lateinit var homeBtn: Button
    lateinit var localTv: TextView
    lateinit var db: AppDatabase

>>>>>>> Stashed changes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)

<<<<<<< Updated upstream
        recyclerView = findViewById(R.id.as_RecyclerV)
        addStoryBtn = findViewById(R.id.as_AddStoryBtn)
        localTv = findViewById(R.id.as_LocalTV)
=======
        initView()
        callDB()
>>>>>>> Stashed changes

        var intent = intent
        val mapId = intent.getIntExtra("Local", 0)
        val output = db.mapDao().getAllById(mapId)[0]

<<<<<<< Updated upstream
        localTv.text = intent.getStringExtra("Local")

        addStoryBtn.setOnClickListener {
            var intent = Intent(applicationContext,AddStoryActivity::class.java)
=======
        localTv.text = output.mapName

        addStoryBtn.setOnClickListener {
            intent = Intent(applicationContext, AddStoryActivity::class.java)
            startActivity(intent)
        }
        changeColorBtn.setOnClickListener {
            intent = Intent(applicationContext, ChangeColorActivity::class.java)
            intent.putExtra("Local", mapId)
            startActivity(intent)
        }
        homeBtn.setOnClickListener {
            intent = Intent(applicationContext, MapActivity::class.java)
>>>>>>> Stashed changes
            startActivity(intent)
            finish()
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

    private fun initView() {
        recyclerView = findViewById(R.id.as_RecyclerV)
        addStoryBtn = findViewById(R.id.as_AddStoryBtn)
        changeColorBtn = findViewById(R.id.as_ChangeColorBtn)
        homeBtn = findViewById(R.id.as_HomeBtn)
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