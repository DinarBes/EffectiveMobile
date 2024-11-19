package com.example.effectivemobile.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun FirebaseAuth(): FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }
//
//    @Provides
//    @Singleton
//    fun FirebaseApp(
//        @ApplicationContext context: Context
//    ) : FirebaseApp {
//        return FirebaseApp.initializeApp(context) ?:
//        throw IllegalStateException("FirebaseApp initialization failed")
//    }
//}