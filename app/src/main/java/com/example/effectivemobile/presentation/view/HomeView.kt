package com.example.effectivemobile.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.effectivemobile.presentation.items.CourseItem
import com.example.effectivemobile.presentation.items.SheetCourse
import com.example.effectivemobile.presentation.viewmodel.HomeViewModel
import com.example.effectivemobile.ui.theme.DarkGrey
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    homeViewModel: HomeViewModel
) {

    val courses = homeViewModel.courses.collectAsState()

    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()
    val content: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(content) }

    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        onDismissRequest = {
            scope.launch {
                modalBottomSheetState.hide()
            }
        },
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(color = DarkGrey)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            courses.value?.let {
                items(it) { course ->
                    CourseItem(
                        image = course.cover,
                        date = course.createDate,
                        title = course.title,
                        summary = course.summary,
                        price = course.price
                    ) {
                        customSheetContent = {
                            SheetCourse(
                                modalBottomSheetState = modalBottomSheetState,
                                course = course
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