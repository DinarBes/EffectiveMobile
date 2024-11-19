package com.example.effectivemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.storage.local.BookmarkEntity
import com.example.data.storage.local.CourseDatabase
import com.example.data.storage.local.CourseEntity
import com.example.data.storage.mappers.toCourse
import com.example.domain.models.Bookmark
import com.example.domain.models.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
        private val courseDb: CourseDatabase
): ViewModel() {

    private val _bookmarks = MutableStateFlow<List<BookmarkEntity>?>(null)
    val bookmarks = _bookmarks.asStateFlow()

    fun loadAllBookmarks() {
        viewModelScope.launch {
            try {
                _bookmarks.value = courseDb.dao.loadAllBookmarks().map { it }
            } catch (e: Exception){
                Log.e("MainViewModel", e.toString())
                _bookmarks.value = listOf()
            }
        }
    }

    fun upsertBookmarks(bookmarksEntity: BookmarkEntity) {
        viewModelScope.launch {
            courseDb.dao.upsertBookmark(bookmarksEntity)
        }
    }

    fun isRowIsExist(id: Int): Boolean {
        return courseDb.dao.isRowIsExist(id)
    }

    fun deleteBookmarks(bookmarksID: Int) {
        viewModelScope.launch {
            courseDb.dao.deleteBookmarks(bookmarksID)
        }
    }
}