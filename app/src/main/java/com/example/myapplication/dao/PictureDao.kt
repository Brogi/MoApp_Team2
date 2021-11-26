package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.Map
import com.example.myapplication.model.Picture
import com.example.myapplication.model.Story

@Dao
interface PictureDao {

    @Insert
    fun insertPicture(picture : Picture)

    @Query("SELECT * FROM picture WHERE storyId = :id")
    fun getAllById(id: Int): List<Picture>

    @Query("SELECT COUNT(*) FROM picture")
    fun getAllCount() : Int

}