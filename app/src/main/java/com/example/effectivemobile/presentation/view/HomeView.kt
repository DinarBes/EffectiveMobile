package com.example.effectivemobile.presentation.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.effectivemobile.presentation.items.CourseItem
import com.example.effectivemobile.presentation.items.SheetCourse
import com.example.effectivemobile.presentation.viewmodel.HomeViewModel
import com.example.effectivemobile.ui.theme.Background
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    homeViewModel: HomeViewModel
) {

    val courses = homeViewModel.coursesFlow.collectAsLazyPagingItems()

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
        sheetBackgroundColor = Color.White,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            if (courses.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(Background),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(count = courses.itemCount) { index ->
                        val item = courses[index]
                        item?.let {
                            CourseItem(
                                image = it.cover,
                                date = it.createDate,
                                title = it.title,
                                summary = it.summary,
                                price = it.price
                            ) {
                                customSheetContent = {
                                    SheetCourse(
                                        modalBottomSheetState = modalBottomSheetState,
                                        course = it
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