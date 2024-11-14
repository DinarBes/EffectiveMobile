package com.example.effectivemobile.presentation.items

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.effectivemobile.R
import com.example.effectivemobile.ui.theme.DarkGrey
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto

@Composable
fun PasswordView(
    nameField: String,
    passwordState: MutableState<String>
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val enabled = remember { mutableStateOf(false) }

    val enterPassword = stringResource(id = R.string.password_empty)

    TextField(
        value = passwordState.value,
        onValueChange = { newText ->
            passwordState.value = newText
        },
        textStyle = MaterialTheme.typography.titleSmall,
        visualTransformation = if (enabled.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (passwordState.value.isNotEmpty()) {
                        enabled.value = !enabled.value
                    } else {
                        Toast.makeText(context, enterPassword, Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Icon(
                    painter = if (enabled.value) painterResource(id = R.drawable.open_eye) else painterResource(id = R.drawable.close_eye),
                    contentDescription = null,
                    tint = if (enabled.value) Green else LightGrey,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
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