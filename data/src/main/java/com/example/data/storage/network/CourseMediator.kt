package com.example.data.storage.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.storage.local.CourseDatabase
import com.example.data.storage.local.CourseEntity
import com.example.data.storage.mappers.toCourseEntity
import com.example.domain.repository.UserRepository
import io.ktor.client.plugins.HttpRequestTimeoutException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CourseMediator(
    private val courseDb: CourseDatabase,
    private val userRepository: UserRepository
): RemoteMediator<Int, CourseEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CourseEntity>
    ): MediatorResult {

        return try {
            // текущий ключ загрузки
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 2
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        2
                    }
                    else {
                        (lastItem.key / state.config.pageSize) + 1
                    }
                }
            }

            Log.e("Loadkey", loadKey.toString())
            val courses = userRepository.getCourses(page = loadKey)

            courseDb.withTransaction {
                val courseEntities = courses.mapNotNull {
                    if (!courseDb.dao.findById(it.id)) {
                        it.toCourseEntity()
                    } else {
                        null
                    }
                }

                courseDb.dao.upsertAll(courseEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = courses.isEmpty()
            )
        } catch (error: IOException){
            Log.e("CourseMediatorIOException", error.toString())
            MediatorResult.Error(error)
        } catch (error: HttpRequestTimeoutException) {
            Log.e("CourseMediatorHttpRequestTimeoutException", error.toString())
            MediatorResult.Error(error)
        }
    }
}