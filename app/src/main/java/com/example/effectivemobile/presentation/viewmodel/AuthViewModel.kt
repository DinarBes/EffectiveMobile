package com.example.effectivemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.storage.model.AuthState
import com.example.domain.usecase.AuthorizationUseCase
import com.example.domain.usecase.SubscribeToTopicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase,
    private val subscribeToTopicUseCase: SubscribeToTopicUseCase
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Success)
    val authState = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                subscribeToTopicUseCase.subscribeToTopic("")
            } catch (error: Exception) {
                Log.e("Subscribe to topic vm error", error.toString())
            }
        }
    }

    fun registrationUser(login: String, password: String) {
        viewModelScope.launch {
            try {
                authorizationUseCase.registrationUser(login, password)
                _authState.emit(AuthState.Success)
            } catch (error: Exception) {
                _authState.emit(AuthState.Error(error.message))
            }
        }
    }

    fun authUser(login: String, password: String) {
        viewModelScope.launch {
            try {
                authorizationUseCase.authUser(login, password)
                _authState.emit(AuthState.Success)
            } catch (error: Exception) {
                Log.e("Auth user vm error", error.toString())
                _authState.emit(AuthState.Error(error.message))
            }
        }
    }
}