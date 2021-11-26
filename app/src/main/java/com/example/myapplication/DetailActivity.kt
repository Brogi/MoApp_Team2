package com.example.myapplication

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class DetailActivity : AppCompatActivity() {
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private lateinit var gestureDetector: GestureDetector
    private var scaleFactor = 1.0f
    private val constraintLayout: ConstraintLayout by lazy {
        findViewById(R.id.constraintLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        gestureDetector = GestureDetector(this, GestureListener())
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageBitmap(getIntent().getParcelableExtra("param"))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector!!.onTouchEvent(event)
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        mScaleGestureDetector!!.onTouchEvent(ev)
        gestureDetector.onTouchEvent(ev)
        return gestureDetector.onTouchEvent(ev)
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= mScaleGestureDetector!!.scaleFactor

            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 4.0f))

            constraintLayout.scaleX = scaleFactor
            constraintLayout.scaleY = scaleFactor
            return true
        }
    }

    inner class GestureListener: GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return true
        }
    }
}
