package com.example.effectivemobile.presentation.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.effectivemobile.ui.theme.DarkGrey
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto

@Composable
fun FormView(
    nameField: String,
    text: MutableState<String>
) {

    val focusManager = LocalFocusManager.current

    TextField(
        value = text.value,
        onValueChange = { newText ->
            text.value = newText
        },
        textStyle = MaterialTheme.typography.titleSmall.copy(
            fontFamily = Roboto,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            color = LightGrey
        ),
        label = {
            Text(
                text = nameField,
                color = LightGrey.copy(alpha = 0.5f),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = LightGrey,
            unfocusedTextColor = LightGrey.copy(0.5f),
            cursorColor = LightGrey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = DarkGrey,
            unfocusedContainerColor = DarkGrey
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
    )
}