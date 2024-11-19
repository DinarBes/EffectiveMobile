package com.example.effectivemobile.di

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.AuthorizationUseCase
import com.example.domain.usecase.GetCoursesUseCase
import com.example.domain.usecase.SubscribeToTopicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAuthorizationUseCase(userRepository: UserRepository): AuthorizationUseCase {
        return AuthorizationUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideGetCoursesUseCase(userRepository: UserRepository): GetCoursesUseCase {
        return GetCoursesUseCase(userRepository = userRepository)
    }

    @Provides
    fun subscribeToTopicUseCase(userRepository: UserRepository): SubscribeToTopicUseCase {
        return SubscribeToTopicUseCase(userRepository = userRepository)
    }
}