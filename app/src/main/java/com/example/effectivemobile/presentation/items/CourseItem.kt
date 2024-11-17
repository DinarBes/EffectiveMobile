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
fun CourseItem(
    image: String,
    date: String,
    title: String,
    summary: String,
    price: Int?,
    action: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { action() },
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopSelection(
            image = image,
            date = date
        )
        BottomSelection(
            title = title,
            summary = summary,
            price = price
        )
    }
}

@Composable
fun TopSelection(
    image: String,
    date: String
) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
        AsyncImage(
            model = image,
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
            text = date,
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
fun BottomSelection(
    title: String,
    summary: String,
    price: Int?
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = title,
            fontFamily = Roboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = LightGrey
        )
        Text(
            text = summary,
            fontFamily = Roboto,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            maxLines = 2,
            softWrap = true,
            color = LightGrey.copy(0.7f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = price.toString() + " ₽",
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