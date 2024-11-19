package com.example.domain.usecase

import com.example.domain.repository.UserRepository

class SubscribeToTopicUseCase(private val userRepository: UserRepository) {

    suspend fun subscribeToTopic(vararg topics: String) {
        userRepository.subscribeToTopic(topics.toString())
    }
}