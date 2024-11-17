package com.example.data.storage

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.storage.local.CourseEntity

@Dao
interface CourseDao {


    /**
     * Метод для добавления курса в кэш
     */
    @Upsert
    suspend fun upsertAll(news: List<CourseEntity>)

    /**
     * Метод для поиска курса из кэша по ID
     */
    @Query("SELECT COUNT(*) FROM CourseEntity WHERE id = :id")
    fun findById(id: Int): Boolean

    /**
     * Метод для очистки кэша
     */
    @Query("DELETE FROM CourseEntity")
    suspend fun clearAll()

    /**
     * Метод для получения курса из кэша по ID
     */
    @Query("SELECT * FROM CourseEntity WHERE id = :id")
    suspend fun takeCourseById(id: Int): CourseEntity?

    /**
     * Метод для добавления курса в закладки
     */
    @Upsert
    suspend fun upsertBookmark(courseEntity: CourseEntity)

    /**
     * Метод для загрузки всех закладок
     */
    @Query("SELECT * FROM CourseEntity")
    fun loadAllBookmarks(): Array<CourseEntity>

    /**
     * Метод удаления новости из закладок
     */
    @Query("DELETE FROM CourseEntity WHERE id = :bookmarksId")
    suspend fun deleteBookmarks(bookmarksId: Int)

    /**
     * Метод для проверки есть ли новость в закладках
     */
    @Query("SELECT EXISTS(SELECT * FROM CourseEntity WHERE id = :id)")
    fun isRowIsExist(id: Int): Boolean
}