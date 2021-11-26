package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.example.myapplication.model.Converter
import com.example.myapplication.model.Picture
import com.example.myapplication.model.Story
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception
import java.util.*

class AddStoryActivity : AppCompatActivity() {
    lateinit var dp : DatePicker
    lateinit var edtDiary : EditText
    lateinit var btnWrite : Button
    lateinit var fileName : String
    lateinit var hash : String
    lateinit var btnHash: Button
    lateinit var filename1 : String
    lateinit var db : AppDatabase
    lateinit var image1 : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstory)
        title = "게시글 작성하기"

        image1 = findViewById<ImageView>(R.id.image1)

        dp = findViewById<DatePicker>(R.id.datePicker1)
        edtDiary = findViewById<EditText>(R.id.edtDiary)
        btnWrite = findViewById<Button>(R.id.btnWrite)

        var cal = Calendar.getInstance()
        var cYear = cal.get(Calendar.YEAR)
        var cMonth = cal.get(Calendar.MONTH)
        var cDay = cal.get(Calendar.DAY_OF_MONTH)

        var intent = intent
        val mapId = intent.getIntExtra("Local", 0)

        filename1 =Integer.toString(cYear)+"_"+Integer.toString(cMonth+1)+"_"+Integer.toString(cDay)+".txt"
        var str1=readDiary(filename1)
        edtDiary.setText(str1)
        btnWrite.isEnabled = true

        callDB()

        dp.init(cYear, cMonth, cDay) { view, year, monthOfYear, dayOfMonth ->
            fileName = (Integer.toString(year) + "_"
                    + Integer.toString(monthOfYear + 1) + "_"
                    + Integer.toString(dayOfMonth) + ".txt")
            var str = readDiary(fileName)
            edtDiary.setText(str)
            btnWrite.isEnabled = true
        }

        btnWrite.setOnClickListener {
            try{
                // 동적으로 수정하기
                db.storyDao().insertStory(Story(null,mapId,"1111.11.11","Hello world","JODDAEDDA!"))
                val storycount = db.storyDao().getAllCount()
                db.pictureDao().insertPicture(Picture(null,storycount, BitmapFactory.decodeFile("/storage/emulated/0/DCIM/airplane.png")))
            }catch (e : Exception){
                Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_LONG).show()
            }


            var intent = Intent(applicationContext, StoriesActivity::class.java)
            intent.putExtra("Local", mapId)
            startActivity(intent)
            finish()
        }
    }



    fun readDiary(fName: String) : String? {
        var diaryStr : String? = null
        var inFs : FileInputStream
        try {
            inFs = openFileInput(fName)
            var txt = ByteArray(500)
            inFs.read(txt)
            inFs.close()
            diaryStr = txt.toString(Charsets.UTF_8).trim()
            btnWrite.text = "수정 하기"
        } catch (e : IOException) {
            edtDiary.hint = "여러분의 새로운 이야기를 적어주세요"
            btnWrite.text = "업로드"
        }
        return diaryStr
    }

    private fun callDB() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mapDB"
        ).allowMainThreadQueries().build()
    }
}