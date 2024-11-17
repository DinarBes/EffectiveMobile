package com.example.effectivemobile.presentation.items

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.models.Course
import com.example.effectivemobile.R
import com.example.effectivemobile.ui.theme.Background
import com.example.effectivemobile.ui.theme.ButtonGrey
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetCourse(
    modalBottomSheetState: SheetState,
    course: Course
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = course.cover,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Background,
                    modifier = Modifier
                        .background(
                            color = LightGrey,
                            shape = CircleShape
                        )
                        .size(40.dp)
                        .align(Alignment.TopStart)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.bookmark),
                    contentDescription = null,
                    tint = Background,
                    modifier = Modifier
                        .background(
                            color = LightGrey,
                            shape = CircleShape
                        )
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
        Text(
            text = course.title,
            fontFamily = Roboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = LightGrey
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
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
                .align(Alignment.End),
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
            fontSize = 22.sp
        )
        Text(
            text = stringResource(id = R.string.go_to_platform),
            color = LightGrey.copy(0.7f),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp
        )
    }
}