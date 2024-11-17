package com.example.effectivemobile.presentation.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.effectivemobile.R
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto

@Composable
fun CourseItem() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopSelection()
        BottomSelection()
    }
}

@Composable
fun TopSelection() {

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
        AsyncImage(
            model = "",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.bookmark),
                contentDescription = null,
                tint = LightGrey,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = LightGrey.copy(0.5f))
                    .align(Alignment.TopEnd)
            )
        }
        Text(
            text = "",
            fontFamily = Roboto,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            color = LightGrey,
            modifier = Modifier
                .clip(CircleShape)
                .background(color = LightGrey.copy(0.5f))
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun BottomSelection() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Java-developer",
            fontFamily = Roboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = LightGrey
        )
        Text(
            text = "Text text text",
            fontFamily = Roboto,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            color = LightGrey.copy(0.7f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "999 Р",
                fontFamily = Roboto,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = LightGrey
            )
            Text(
                text = "Подробнее ->",
                fontFamily = Roboto,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Green,
                modifier = Modifier
                    .clickable {

                    }
            )
        }
    }
}