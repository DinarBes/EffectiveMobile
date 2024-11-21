package com.example.effectivemobile.presentation.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.effectivemobile.presentation.items.CourseItem
import com.example.effectivemobile.presentation.items.SearchBar
import com.example.effectivemobile.presentation.items.SheetCourse
import com.example.effectivemobile.presentation.items.SortedItem
import com.example.effectivemobile.presentation.viewmodel.BookmarksViewModel
import com.example.effectivemobile.presentation.viewmodel.HomeViewModel
import com.example.effectivemobile.presentation.viewmodel.SearchViewModel
import com.example.effectivemobile.presentation.viewmodel.SortedState
import com.example.effectivemobile.ui.theme.Background
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    homeViewModel: HomeViewModel,
    bookmarksViewModel: BookmarksViewModel,
    searchViewModel: SearchViewModel
) {
    val courses = homeViewModel.coursesFlow.collectAsLazyPagingItems()
    val course = searchViewModel.course.collectAsState()
    val sortedState = homeViewModel.sortedState.collectAsState()

    LaunchedEffect(courses.itemSnapshotList.items) {
        searchViewModel.getCourses(courses.itemSnapshotList.items)
        when (sortedState.value) {
            SortedState.SortedByRating -> courses.itemSnapshotList.items.sortedBy { it.rating }
            SortedState.SortedByDate -> courses.itemSnapshotList.items.sortedBy { it.createDate }
            else -> {}
        }
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val content: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(content) }
    var loading = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = courses.loadState) {
        if (courses.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (courses.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
            Log.e("HomeView", (courses.loadState.refresh as LoadState.Error).error.message.toString())
        } else if (courses.loadState.refresh is LoadState.Loading) {
            loading.value
        } else {
            loading.value = false
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetElevation = 8.dp,
        sheetBackgroundColor = Background,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (modalBottomSheetState.isVisible) {
                    customSheetContent()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                )
            } else {
                SearchBar(searchViewModel = searchViewModel) {
                    customSheetContent = {
                        course.value?.let {
                            SheetCourse(
                                modalBottomSheetState = modalBottomSheetState,
                                course = it,
                                bookmarksViewModel = bookmarksViewModel
                            )
                        }
                    }
                    scope.launch {
                        searchViewModel.onToogleSearch()
                        modalBottomSheetState.show()
                    }
                }
                SortedItem(homeViewModel = homeViewModel)
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Background),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(courses.itemSnapshotList.items) {
                        CourseItem(
                            course = it,
                            bookmarksViewModel = bookmarksViewModel
                        ) {
                            customSheetContent = {
                                SheetCourse(
                                    modalBottomSheetState = modalBottomSheetState,
                                    course = it,
                                    bookmarksViewModel = bookmarksViewModel
                                )
                            }
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                    }
                }
            }
        }
    }
}