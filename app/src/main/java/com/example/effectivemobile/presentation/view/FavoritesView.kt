package com.example.effectivemobile.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.storage.mappers.toCourse
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.items.CourseItem
import com.example.effectivemobile.presentation.items.SheetCourse
import com.example.effectivemobile.presentation.viewmodel.BookmarksViewModel
import com.example.effectivemobile.ui.theme.Background
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto
import kotlinx.coroutines.launch

@Composable
fun FavoritesView(
    bookmarksViewModel: BookmarksViewModel
) {

    val bookmarks = bookmarksViewModel.bookmarks.collectAsState()

    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val content: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(content) }

    if (bookmarks.value.isNullOrEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.favorites_empty),
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                fontSize = 26.sp,
                color = LightGrey
            )
        }
    } else {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Background),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                bookmarks.value?.let {
                    items(it) { bookmark ->
                        CourseItem(
                            course = bookmark.toCourse(),
                            bookmarksViewModel = bookmarksViewModel
                        ) {
                            customSheetContent = {
                                SheetCourse(
                                    modalBottomSheetState = modalBottomSheetState,
                                    course = bookmark.toCourse(),
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