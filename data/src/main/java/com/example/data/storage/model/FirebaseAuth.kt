package com.example.data.storage.model

sealed class AuthState {
    data object Success: AuthState()
    class Error(val message: String?): AuthState()
}