package com.example.effectivemobile.di

import android.content.Context
import androidx.room.Room
import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.local.CourseDatabase
import com.example.data.storage.repository.Repository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideCourseDatabase(@ApplicationContext context: Context): CourseDatabase {
        return Room.databaseBuilder(
            context,
            CourseDatabase::class.java,
            "course.db"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideCoursePager(courseDb: CourseDatabase, userRepository: UserRepository): Repository {
        return Repository(courseDb = courseDb, userRepository = userRepository)
    }
}