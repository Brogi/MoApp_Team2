package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageResource(getIntent().getIntExtra("param", 0))
    }
}
