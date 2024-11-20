package com.example.effectivemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.storage.mappers.toCourse
import com.example.data.storage.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _currentPage = MutableStateFlow(5)

    @OptIn(ExperimentalCoroutinesApi::class)
    val coursesFlow = _currentPage.flatMapLatest { page ->
        repository.getAllCourses(page).flow.map { pagingData ->
            pagingData.map { it.toCourse() }
        }.catch { cause: Throwable ->
            Log.e("HomeViewModel error", cause.message.toString())
        }
    }.cachedIn(viewModelScope)
}