package com.example.domain.repository

import com.example.domain.models.Course

interface UserRepository {

    suspend fun registrationUser(login: String, password: String)

    suspend fun authUser(login: String, password: String)

    suspend fun getCourses(): List<Course>
}