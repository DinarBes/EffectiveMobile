package com.example.domain.usecase

import com.example.domain.models.Course
import com.example.domain.repository.UserRepository

class GetCoursesUseCase(private val userRepository: UserRepository) {

    suspend fun getCourses(page: Int): List<Course> {
        return userRepository.getCourses(page = page)
    }
}