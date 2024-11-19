package com.example.effectivemobile.presentation.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.extension.dateToString
import com.example.effectivemobile.ui.theme.Background
import com.example.effectivemobile.ui.theme.ButtonGrey
import com.example.effectivemobile.ui.theme.DarkGrey
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto
import kotlinx.coroutines.launch

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
            .background(
                color = ButtonGrey,
                shape = RoundedCornerShape(16.dp)
            )
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
            price = price,
            action = action
        )
    }
}

@Composable
fun TopSelection(
    image: String,
    date: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(120.dp),
            contentScale = ContentScale.FillWidth
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.bookmark),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = DarkGrey.copy(0.5f))
                        .padding(5.dp)
                        .size(20.dp)
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
                text = date.dateToString("yyyy-MM-dd'T'HH:mm:ss", "dd MMMM yyyy"),
                fontFamily = Roboto,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                color = LightGrey,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun BottomSelection(
    title: String,
    summary: String,
    price: Int?,
    action: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = if (price != null) "$price ₽" else "Бесплатно",
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
                        action()
                    }
            )
        }
    }
}