package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
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
    lateinit var deleteStoryBtn : FloatingActionButton
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
        deleteStoryBtn.setOnClickListener {
            if (dataL.size <= 0){
                Toast.makeText(applicationContext,"삭제할 스토리가 없습니다",Toast.LENGTH_SHORT).show()
            }
            else{
                var dateArray : Array<String?> = arrayOfNulls<String>(dataL.size)
                var checkArray : BooleanArray = BooleanArray(dataL.size)
                var selectedDate = arrayListOf<String>()
                for (i in dateArray.indices){
                    dateArray[i] = dataL[i].date
                    checkArray[i] = false
                }
                var deleteDialog = AlertDialog.Builder(this@StoriesActivity)
                deleteDialog.setTitle("삭제할 스토리를 선택하세요")
                deleteDialog.setMultiChoiceItems(dateArray,checkArray){ dialog, which, isChecked ->
                    if(isChecked){
                        selectedDate.add(dateArray[which]!!)
                    }
                    else{
                        selectedDate.remove(dateArray[which]!!)
                    }
                }
                deleteDialog.setPositiveButton("확인",DialogInterface.OnClickListener { dialog, which ->
                    if(selectedDate.size > 0){
                        var toBeRemoved = arrayListOf<ThumbNailData>()
                        for(sel in selectedDate){
                            for(i in dataL){
                                if(sel == i.date){
                                    db.storyDao().deleteByStoryId(i.storyId)
                                    db.pictureDao().deleteByStoryId(i.storyId)
                                    toBeRemoved.add(i)
                                }
                            }
                        }
                        for(r in toBeRemoved){
                            dataL.remove(r)
                        }
                        Toast.makeText(applicationContext,"${selectedDate.size}개 스토리 삭제 완료",Toast.LENGTH_SHORT).show()
                        var delDataL = dataL
                        var delAdapter = StoriesAdapter(this,delDataL)
                        recyclerView.adapter = delAdapter
                    }
                })
                deleteDialog.setNegativeButton("취소",null)
                deleteDialog.show()
            }
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
            deleteStoryBtn.startAnimation(fabClose)
            addStoryBtn.isClickable = false
            changeColorBtn.isClickable = false
            deleteStoryBtn.isClickable = false
            isFabOpen = false
        } else {
            floatingBtn.setImageResource(R.drawable.ic_close)
            addStoryBtn.startAnimation(fabOpen)
            changeColorBtn.startAnimation(fabOpen)
            deleteStoryBtn.startAnimation(fabOpen)
            addStoryBtn.isClickable = true
            changeColorBtn.isClickable = true
            deleteStoryBtn.isClickable = true
            isFabOpen = true
        }
    }

    private fun initView() {
        fabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)

        floatingBtn = findViewById(R.id.as_FloatingBtn)
        addStoryBtn = findViewById(R.id.as_AddStoryBtn)
        changeColorBtn = findViewById(R.id.as_ChangeColorBtn)
        deleteStoryBtn = findViewById(R.id.as_DeleteStoryBtn)

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