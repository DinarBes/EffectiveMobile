package com.example.data.storage.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.storage.local.CourseDatabase
import com.example.data.storage.local.CourseEntity
import com.example.data.storage.network.CourseMediator
import com.example.domain.repository.UserRepository

@OptIn(ExperimentalPagingApi::class)
class Repository(
    private val courseDb: CourseDatabase,
    private val userRepository: UserRepository
) {

    fun getAllCourses(page: Int): Pager<Int, CourseEntity> {

        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = CourseMediator(
                courseDb = courseDb,
                userRepository = userRepository
            ),
            pagingSourceFactory = {
                courseDb.dao.pagingSource(page = page)
            }
        )
    }
}