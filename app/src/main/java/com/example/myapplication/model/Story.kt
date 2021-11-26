package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Story(
    @PrimaryKey(autoGenerate = true) val storyId : Int?,
    val mapId : Int,
    val date : String,
    val hashTag : String?,
    val storyContent : String?
)