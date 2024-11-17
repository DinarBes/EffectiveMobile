package com.example.data.storage.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity(
    @PrimaryKey val id: Int,
    val summary: String,
    val cover: String,
    val description: String,
    val totalUnits: Int,
    val lessonsCount: Int,
    val price: Int? = null,
    val title: String,
    val createDate: String
)