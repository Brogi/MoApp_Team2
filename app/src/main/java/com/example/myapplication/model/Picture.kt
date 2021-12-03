package com.example.myapplication.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Picture(
    @PrimaryKey(autoGenerate = true) val imageId : Int?,
    val storyId : Int,
    val image : Uri?
)