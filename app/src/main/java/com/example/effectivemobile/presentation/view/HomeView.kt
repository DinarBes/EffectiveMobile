package com.example.effectivemobile.presentation.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.effectivemobile.presentation.items.CourseItem
import com.example.effectivemobile.presentation.items.SheetCourse
import com.example.effectivemobile.presentation.viewmodel.BookmarksViewModel
import com.example.effectivemobile.presentation.viewmodel.HomeViewModel
import com.example.effectivemobile.presentation.viewmodel.SearchViewModel
import com.example.effectivemobile.ui.theme.Background
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    homeViewModel: HomeViewModel,
    bookmarksViewModel: BookmarksViewModel,
    searchViewModel: SearchViewModel
) {

    val courses = homeViewModel.coursesFlow.collectAsLazyPagingItems()
    val course = searchViewModel.course.collectAsState()

    LaunchedEffect(courses.itemSnapshotList.items) {
        searchViewModel.getCourses(courses.itemSnapshotList.items)
    }

    val searchText = searchViewModel.searchText.collectAsState()
    val isSearching = searchViewModel.isSearching.collectAsState()
    val coursesList = searchViewModel.coursesList.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val content: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(content) }

    LaunchedEffect(key1 = courses.loadState) {
        if (courses.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (courses.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
            Log.e("HomeView", (courses.loadState.refresh as LoadState.Error).error.message.toString())
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
            if (courses.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                SearchBar(
                    query = searchText.value,
                    onQueryChange = searchViewModel::onSearchTextChange,
                    onSearch = searchViewModel::onSearchTextChange,
                    active = isSearching.value,
                    onActiveChange = { searchViewModel.onToogleSearch() },
                    placeholder = {
                        Text(text = "Enter your query")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
                    },
                    trailingIcon = {
                        if (isSearching.value) {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (searchText.value.isNotEmpty()) {
                                        searchViewModel.onSearchTextChange("")
                                    } else {
                                        searchViewModel.onToogleSearch()
                                    }
                                },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close icon"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(coursesList.value) { courseInfo ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Sharp.Refresh,
                                    contentDescription = null,
                                    tint = LightGrey
                                )
                                Text(
                                    text = courseInfo.title,
                                    fontFamily = Roboto,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W400,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .clickable {
                                            searchViewModel.takeCourseById(courseInfo.id)
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
                                )
                            }
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Background),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(count = courses.itemCount) { index ->
                        val item = courses[index]
                        item?.let {
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
}