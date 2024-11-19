package com.example.data.storage.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.storage.CourseDao

@Database(
    entities = [CourseEntity::class, BookmarkEntity::class],
    version = 1
)
abstract class CourseDatabase: RoomDatabase() {

    abstract val dao: CourseDao
}