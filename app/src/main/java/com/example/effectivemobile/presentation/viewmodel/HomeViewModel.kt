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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _sortedState = MutableStateFlow<SortedState>(SortedState.SortedWait)
    val sortedState = _sortedState.asStateFlow()

    private val _currentPage = MutableStateFlow(2)

    @OptIn(ExperimentalCoroutinesApi::class)
    val coursesFlow = _currentPage.flatMapLatest { page ->
        repository.getAllCourses(page).flow.map { pagingData ->
            pagingData.map { it.toCourse() }
        }.catch { cause: Throwable ->
            Log.e("HomeViewModel error", cause.message.toString())
        }
    }.cachedIn(viewModelScope)

    fun sortedByDate() {
        viewModelScope.launch {
            _sortedState.emit(SortedState.SortedByDate)
            coursesFlow.map {

            }
        }
    }

    fun sortedByRating() {
        viewModelScope.launch {
            _sortedState.emit(SortedState.SortedByRating)
        }
    }
}

sealed class SortedState(val text: String) {
    data object SortedWait: SortedState("Сортировка ")
    data object SortedByDate: SortedState("По дате добавления ")
    data object SortedByRating: SortedState("По рейтингу ")
}