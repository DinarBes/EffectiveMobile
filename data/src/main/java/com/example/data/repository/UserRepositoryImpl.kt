package com.example.data.repository

import com.example.data.storage.network.FirebaseNetwork
import com.example.domain.repository.UserRepository

class UserRepositoryImpl: UserRepository {

    override suspend fun registrationUser(login: String, password: String) {
        FirebaseNetwork.registration(login, password)
    }

    override suspend fun authUser(login: String, password: String) {
        FirebaseNetwork.auth(login, password)
    }
}