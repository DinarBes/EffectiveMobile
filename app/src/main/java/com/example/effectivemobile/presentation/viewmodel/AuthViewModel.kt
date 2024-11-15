package com.example.effectivemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.storage.model.AuthState
import com.example.data.storage.network.FirebaseNetwork
import com.example.domain.usecase.AuthorizationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase
): ViewModel() {

    private val _authState = MutableSharedFlow<AuthState>()
    val authState = _authState.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                FirebaseNetwork.subscribeToTopics("")
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
                Log.e("Reg user vm error", error.toString())
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