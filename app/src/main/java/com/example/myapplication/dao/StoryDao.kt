package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.model.Map
import com.example.myapplication.model.Story

@Dao
interface StoryDao {

    @Query("SELECT * FROM story WHERE mapId = :id")
    fun getAllById(id: Int): List<Story>

    @Insert
    fun insertStory(story : Story)

    @Query("SELECT COUNT(*) FROM story")
    fun getAllCount() : Int

    @Query("SELECT * FROM story WHERE storyId = :id")
    fun getByStoryId(id : Int): Story
}