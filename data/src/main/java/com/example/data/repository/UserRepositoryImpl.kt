package com.example.data.repository

import com.example.data.storage.network.FirebaseNetwork
import com.example.data.storage.network.StepikNetwork
import com.example.domain.models.Course
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class UserRepositoryImpl: UserRepository {

    override suspend fun registrationUser(login: String, password: String) {
        FirebaseNetwork.registration(login, password)
    }

    override suspend fun authUser(login: String, password: String) {
        FirebaseNetwork.auth(login, password)
    }

    override suspend fun getCourses(page: Int): List<Course> {

        val courseModel = StepikNetwork.getCourses(page = page)

        return coroutineScope {
            courseModel.courses.map {
                async {
                    Course(
                        id = it.id,
                        summary = it.summary,
                        cover = it.cover,
                        description = it.description,
                        totalUnits = it.totalUnits,
                        lessonsCount = it.lessonsCount,
                        price = it.price,
                        title = it.title,
                        createDate = it.createDate,
                        page = courseModel.meta.page
                    )
                }
            }.awaitAll()
        }
    }
}