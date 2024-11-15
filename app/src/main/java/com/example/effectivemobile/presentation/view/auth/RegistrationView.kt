package com.example.effectivemobile.presentation.view.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.storage.model.AuthState
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.items.FormView
import com.example.effectivemobile.presentation.items.PasswordView
import com.example.effectivemobile.presentation.navigation.Screen
import com.example.effectivemobile.presentation.viewmodel.AuthViewModel
import com.example.effectivemobile.ui.theme.Blue
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.Grey
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Orange
import com.example.effectivemobile.ui.theme.Roboto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegistrationView(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val enabled = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.registration),
            fontFamily = Roboto,
            fontSize = 28.sp,
            fontWeight = FontWeight.W400,
            color = LightGrey,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        RegistrationInfo(
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            shape = RoundedCornerShape(30.dp),
            enabled = enabled.value,
            onClick = {
                if (email.value.isEmpty() || password.value.isEmpty() || confirmPassword.value.isEmpty()) {
                    enabled.value = false
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else if (password.value != confirmPassword.value) {
                    enabled.value = false
                    Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else {
                    enabled.value = true
                    CoroutineScope(Dispatchers.IO).launch {
                        authViewModel.authState.collect { authState ->
                            when (authState) {
                                is AuthState.Success -> authViewModel.registrationUser(
                                    login = email.value,
                                    password = password.value
                                )
                                is AuthState.Error -> {
                                    Log.e("Error", authState.message ?: "Unknown error")
                                    Toast.makeText(context, authState.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.registration),
                color = LightGrey,
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        UserCreated(navController = navController)
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Grey
        )
        SocialMedia()
    }
}

@Composable
fun RegistrationInfo(
    email: MutableState<String>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        FormView(
            nameField = stringResource(id = R.string.email),
            text = email
        )

        PasswordView(
            nameField = stringResource(id = R.string.password),
            passwordState = password
        )

        PasswordView(
            nameField = stringResource(id = R.string.confirm_password),
            passwordState = confirmPassword
        )
    }
}

@Composable
fun UserCreated(
    navController: NavController
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.user_created) + " ",
            color = LightGrey,
            fontFamily = Roboto,
            fontWeight = FontWeight.W600,
            fontSize = 12.sp,
        )
        Text(
            text = stringResource(id = R.string.login),
            color = Green,
            fontFamily = Roboto,
            fontWeight = FontWeight.W600,
            fontSize = 12.sp,
            modifier = Modifier
                .clickable {
                    navController.navigate(route = Screen.AuthScreen.route)
                }
        )
    }
}

@Composable
fun SocialMedia() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier.width(150.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.vk),
                contentDescription = null,
                tint = Color.White,
            )
        }
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            modifier = Modifier.width(150.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ok),
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}