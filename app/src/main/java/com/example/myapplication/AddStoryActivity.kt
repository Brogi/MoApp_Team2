package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
    lateinit var image2 : ImageView
    lateinit var btnImage : Button
    val GALLARY = 111
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstory)
        title = "게시글 작성하기"

        image1 = findViewById<ImageView>(R.id.image1)
        image2 = findViewById(R.id.image2)

        dp = findViewById<DatePicker>(R.id.datePicker1)
        edtDiary = findViewById<EditText>(R.id.edtDiary)
        btnImage = findViewById(R.id.btnImage)
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

        btnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            startActivityForResult(Intent.createChooser(intent,"Load"),GALLARY)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GALLARY){
            when(resultCode){
                RESULT_OK -> {
                    data?.let{
                        val list = arrayListOf<Uri>()

                        when{
                            it.data != null -> list.add(it.data as Uri)
                            it.clipData != null -> {
                                val clip = it.clipData
                                val size = clip?.itemCount

                                for(i in 0 until size!!){
                                    val item = clip.getItemAt(i).uri

                                    list.add(item)
                                }
                            }
                            else -> {}
                        }
                        if(it.data!= null)
                            image1.setImageURI(list[0])
                        else if (it.clipData != null){
                            image1.setImageURI(list[0])
                            image2.setImageURI(list[1])
                        }
                    }
                }
            }
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