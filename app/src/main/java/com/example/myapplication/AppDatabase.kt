package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.dao.MapDao
import com.example.myapplication.dao.PictureDao
import com.example.myapplication.dao.StoryDao
import com.example.myapplication.model.Converter
import com.example.myapplication.model.Map
import com.example.myapplication.model.Picture
import com.example.myapplication.model.Story

@Database(entities = [Map::class, Story::class, Picture::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mapDao(): MapDao
    abstract fun storyDao() : StoryDao
    abstract fun pictureDao() : PictureDao
}