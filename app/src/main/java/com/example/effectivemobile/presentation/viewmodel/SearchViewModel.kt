package com.example.effectivemobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.storage.local.CourseDatabase
import com.example.data.storage.mappers.toCourse
import com.example.domain.models.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val courseDb: CourseDatabase
): ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _courses = MutableStateFlow<List<CourseInfo>>(listOf())
    val courses = _courses.asStateFlow()

    private val _course = MutableStateFlow<Course?>(null)
    val course = _course.asStateFlow()

    val coursesList = searchText
        .combine(courses) { text, courses ->
            if (text.isBlank()) {
                courses
            } else {
                courses.filter { course ->
                    course.title.uppercase().contains(text.trim().uppercase())
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _courses.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun getCourses(courses: List<Course>) {
        viewModelScope.launch {
            _courses.value = courses.map { CourseInfo(it.id, it.title) }
        }
    }

    fun takeCourseById(id: Int) {
        viewModelScope.launch {
            _course.value = courseDb.dao.takeCourseById(id)?.toCourse()
        }
    }
}

data class CourseInfo(
    val id: Int,
    val title: String
)