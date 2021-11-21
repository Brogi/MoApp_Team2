package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.dao.MapDao
import com.example.myapplication.model.Map

@Database(entities = [Map::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mapDao(): MapDao
}