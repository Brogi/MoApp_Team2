package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.model.Map

@Dao
interface MapDao {
    @Query("SELECT * FROM map")
    fun getAll(): List<Map>

    @Query("SELECT * FROM map WHERE mapId = :id")
    fun getAllById(id: Int): List<Map>

    @Query("SELECT COUNT(*) FROM map")
    fun getAllCount(): Int

    @Query("UPDATE map SET color = :color WHERE mapId = :mapId")
    fun updateColor(color: String, mapId: Int)

    @Insert
    fun insertMap(map: Map)

    @Query("DELETE FROM map")
    fun deleteAll()
}