package com.example.domain.usecase

import com.example.domain.repository.UserRepository

class AuthorizationUseCase(private val userRepository: UserRepository) {

    suspend fun registrationUser(login: String, password: String) {
        userRepository.registrationUser(login, password)
    }

    suspend fun authUser(login: String, password: String) {
        userRepository.authUser(login, password)
    }
}