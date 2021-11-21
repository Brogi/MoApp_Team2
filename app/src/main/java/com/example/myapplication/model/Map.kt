package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Map (
    @PrimaryKey val mapId: Int,
    val mapName: String?,
    val color: String?
)