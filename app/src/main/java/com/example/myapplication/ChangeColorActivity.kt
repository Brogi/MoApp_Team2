package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.larswerkman.holocolorpicker.ColorPicker
import com.larswerkman.holocolorpicker.SaturationBar
import com.larswerkman.holocolorpicker.ValueBar
import java.io.File
import java.util.*

class ChangeColorActivity : AppCompatActivity() {
    private lateinit var textViewHEX: TextView

    private lateinit var textViewNewHEX: TextView
    private lateinit var textViewNewRed: TextView
    private lateinit var textViewNewGreen: TextView
    private lateinit var textViewNewBlue: TextView

    private lateinit var originalHex: String

    private lateinit var changeBtn: Button
    private lateinit var cancelBtn: Button

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_color)

        initView()
        callDB()

        val intent = intent
        val mapId = intent.getIntExtra("Local", 0)
        val output = db.mapDao().getAllById(mapId)[0]
        originalHex = output.color.toString()

        textViewHEX.text = "HEX : $originalHex"

        initPicker(0, 0, 0)
        btnClick(mapId)
    }

    private fun initView() {
        textViewHEX = findViewById(R.id.textViewHEX)

        textViewNewHEX = findViewById(R.id.textViewNewHEX)
        textViewNewRed = findViewById(R.id.textViewNewRed)
        textViewNewGreen = findViewById(R.id.textViewNewGreen)
        textViewNewBlue = findViewById(R.id.textViewNewBlue)

        changeBtn = findViewById(R.id.changeBtn)
        cancelBtn = findViewById(R.id.cancelBtn)
    }

    private fun callDB() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mapDB"
        ).allowMainThreadQueries().build()
    }

    private fun btnClick(mapId: Int) {
        changeBtn.setOnClickListener {
            val newColor = textViewNewHEX.text.toString()
            val result =  newColor.substring(newColor.length - 7, newColor.length)
            db.mapDao().updateColor(result, mapId)
            finish()
        }

        cancelBtn.setOnClickListener {
            finish()
        }
    }

    private fun newColorUpdateView(hex: String, red: Int, green: Int, blue: Int) {
        textViewNewHEX.text = "HEX : #$hex"
        textViewNewRed.text = "R $red"
        textViewNewGreen.text = "G $green"
        textViewNewBlue.text = "B $blue"

    }

    private fun initPicker(red: Int, green: Int, blue: Int) {
        var picker: ColorPicker = findViewById(R.id.picker)
        var saturationBar: SaturationBar = findViewById(R.id.saturationBar)
        var valueBar: ValueBar = findViewById(R.id.valueBar)

        picker.addSaturationBar(saturationBar)
        picker.addValueBar(valueBar)

        picker.color = Color.rgb(red, green, blue)
        picker.oldCenterColor = Color.rgb(red, green, blue)
        picker.setNewCenterColor(Color.rgb(red, green, blue))

        picker.setOnColorChangedListener {
            var hexColor = Integer.toHexString(it)
            if (hexColor.length > 1) {
                hexColor = hexColor.substring(2)
            }

            newColorUpdateView(
                hexColor.toUpperCase(Locale.ROOT),
                Integer.parseInt(hexColor.substring(0, 2), 16),
                Integer.parseInt(hexColor.substring(2, 4), 16),
                Integer.parseInt(hexColor.substring(4, 6), 16)
            )

        }
    }
}