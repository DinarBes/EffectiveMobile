package com.example.effectivemobile.presentation.items

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.storage.mappers.toBookmarkEntity
import com.example.data.storage.mappers.toCourseEntity
import com.example.domain.models.Course
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.extension.dateToString
import com.example.effectivemobile.presentation.extension.htmlFormated
import com.example.effectivemobile.presentation.viewmodel.BookmarksViewModel
import com.example.effectivemobile.ui.theme.Background
import com.example.effectivemobile.ui.theme.ButtonGrey
import com.example.effectivemobile.ui.theme.DarkGrey
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto
import kotlinx.coroutines.launch

@Composable
fun SheetCourse(
    modalBottomSheetState: ModalBottomSheetState,
    course: Course,
    bookmarksViewModel: BookmarksViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var checked by remember {
        mutableStateOf(!bookmarksViewModel.isRowIsExist(course.id))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = course.cover,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            modalBottomSheetState.hide()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Background,
                        modifier = Modifier
                            .background(
                                color = LightGrey,
                                shape = CircleShape
                            )
                            .padding(5.dp)
                            .size(40.dp)
                    )
                }
                IconButton(
                    onClick = {
                        if (checked) {
                            bookmarksViewModel.upsertBookmarks(course.toBookmarkEntity())
                            checked = !checked
                            Toast.makeText(context, "Курс добавлен в избранное", Toast.LENGTH_SHORT).show()
                        } else {
                            bookmarksViewModel.deleteBookmarks(course.id)
                            checked = !checked
                            Toast.makeText(context, "Курс удален из избранного", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = null,
                        tint = if (checked) Background else Green,
                        modifier = Modifier
                            .background(
                                color = LightGrey,
                                shape = CircleShape
                            )
                            .padding(5.dp)
                            .size(40.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape)
                    .background(color = DarkGrey.copy(0.5f))
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = course.createDate.dateToString("yyyy-MM-dd'T'HH:mm:ss", "dd MMMM yyyy"),
                    fontFamily = Roboto,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    color = LightGrey,
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
        Text(
            text = course.title,
            fontFamily = Roboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = LightGrey,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            shape = RoundedCornerShape(30.dp),
            onClick = {
                scope.launch {
                    modalBottomSheetState.hide()
                    Toast.makeText(context, "Курс начат", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.start_course),
                color = LightGrey,
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGrey),
            shape = RoundedCornerShape(30.dp),
            onClick = {
                scope.launch {
                    modalBottomSheetState.hide()
                    Toast.makeText(context, "Переход на платформу", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.go_to_platform),
                color = LightGrey,
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Text(
            text = stringResource(id = R.string.course_info),
            color = LightGrey,
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = htmlFormated(course.description),
            color = LightGrey.copy(0.7f),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}