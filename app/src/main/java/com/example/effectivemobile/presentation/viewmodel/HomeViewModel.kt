package com.example.effectivemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Course
import com.example.domain.usecase.GetCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase
): ViewModel() {

    private val _courses = MutableStateFlow<List<Course>?>(null)
    val courses = _courses.asStateFlow()

    fun getCourses() {
        viewModelScope.launch {
            try {
                _courses.value = getCoursesUseCase.getCourses()
            } catch (error: Exception) {
                Log.e("Get course vm error", error.toString())
            }
        }
    }
}