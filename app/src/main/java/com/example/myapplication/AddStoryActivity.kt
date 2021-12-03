package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.model.Converter
import com.example.myapplication.model.Picture
import com.example.myapplication.model.Story
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.util.*

class AddStoryActivity : AppCompatActivity() {
    lateinit var dp : DatePicker
    lateinit var edtDiary : EditText
    lateinit var btnWrite : Button
    lateinit var db : AppDatabase
    lateinit var btnImage : Button
    lateinit var selectedImgRec : RecyclerView
    lateinit var imageUri : ArrayList<Uri>
    lateinit var imageCountTV : TextView
    lateinit var hashEdt : EditText
    val GALLARY = 111
    lateinit var imageAdapter: SelectedImgAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstory)
        title = "게시글 작성하기"


        dp = findViewById<DatePicker>(R.id.datePicker1)
        edtDiary = findViewById<EditText>(R.id.edtDiary)
        btnImage = findViewById(R.id.btnImage)
        btnWrite = findViewById<Button>(R.id.btnWrite)
        imageCountTV = findViewById(R.id.imageCount)
        hashEdt = findViewById(R.id.hash)

        var cal = Calendar.getInstance()
        var cYear = cal.get(Calendar.YEAR)
        var cMonth = cal.get(Calendar.MONTH)
        var cDay = cal.get(Calendar.DAY_OF_MONTH)

        var intent = intent
        val mapId = intent.getIntExtra("Local", 0)

        callDB()

        dp.init(cYear, cMonth, cDay) { view, year, monthOfYear, dayOfMonth ->
            //EMPTY
        }

        btnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            startActivityForResult(Intent.createChooser(intent,"Load"),GALLARY)
        }

        btnWrite.setOnClickListener {
            try{
                var myDate = dp.year.toString() + "." + (dp.month+1).toString() + "." + dp.dayOfMonth.toString()
                // 동적으로 수정하기
                db.storyDao().insertStory(Story(null,mapId,myDate,hashEdt.text.toString(),edtDiary.text.toString()))
                val storycount = db.storyDao().getAllCount()
                for (i in imageUri){
                    db.pictureDao().insertPicture(Picture(null,storycount, i))
                }
            }catch (e : Exception){
                Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_LONG).show()
            }


            var intent = Intent(applicationContext, StoriesActivity::class.java)
            intent.putExtra("Local", mapId)
            startActivity(intent)
            finish()
        }

        selectedImgRec = findViewById(R.id.selectedImgRec)
        selectedImgRec.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)

        imageUri = arrayListOf()
        imageAdapter = SelectedImgAdapter(this,imageUri)
        selectedImgRec.adapter = imageAdapter

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
                                var itemSize = it.clipData!!.itemCount
                                if(itemSize > 10){
                                    Toast.makeText(applicationContext,"사진은 최대 10개 선택가능합니다!\n선택 개수 : $itemSize",Toast.LENGTH_SHORT).show()
                                    return
                                }

                                val clip = it.clipData
                                val size = clip?.itemCount

                                for(i in 0 until size!!){
                                    val item = clip.getItemAt(i).uri

                                    list.add(item)
                                }
                            }
                            else -> {}
                        }
                        // 선택한 사진을 뷰에 넣음
                        imageUri = arrayListOf()
                        for(i in list){
                            imageUri.add(i)
                        }
                        imageAdapter = SelectedImgAdapter(this,imageUri)
                        selectedImgRec.adapter = imageAdapter

                        imageCountTV.text = imageUri.size.toString() + "/10 이미지"
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