package com.example.domain.repository

interface UserRepository {

    suspend fun registrationUser(login: String, password: String)

    suspend fun authUser(login: String, password: String)
}