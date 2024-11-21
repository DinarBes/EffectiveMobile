package com.example.effectivemobile.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto

@Composable
fun ProfileView() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Заглушка",
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 26.sp,
            color = LightGrey
        )
    }
}