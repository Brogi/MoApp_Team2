package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.larswerkman.holocolorpicker.ColorPicker
import com.larswerkman.holocolorpicker.SaturationBar
import com.larswerkman.holocolorpicker.ValueBar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_color)

        textViewHEX = findViewById(R.id.textViewHEX)

        textViewNewHEX = findViewById(R.id.textViewNewHEX)
        textViewNewRed = findViewById(R.id.textViewNewRed)
        textViewNewGreen = findViewById(R.id.textViewNewGreen)
        textViewNewBlue = findViewById(R.id.textViewNewBlue)

        changeBtn = findViewById(R.id.changeBtn)
        cancelBtn = findViewById(R.id.cancelBtn)

        var intent = intent
        originalHex = intent.getStringExtra("OriginalColor").toString()
        textViewHEX.text = "HEX : $originalHex"

        initPicker(0, 0, 0)
        btnClick()
    }

    private fun btnClick() {
        changeBtn.setOnClickListener {
            // DB에 추가
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