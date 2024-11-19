package com.example.effectivemobile.presentation.config

import com.example.effectivemobile.presentation.navigation.Screen

val bottomMenuRoutes = listOf(
    Screen.HomeScreen.route,
    Screen.FavoritesScreen.route,
    Screen.ProfileScreen.route
)

val topBarRoutes = listOf(
    Screen.FavoritesScreen.route,
    Screen.ProfileScreen.route
)

fun getLabelForRoute(route: String?): String {

    when (route) {
        Screen.HomeScreen.route -> return Screen.HomeScreen.title
        Screen.FavoritesScreen.route -> return Screen.FavoritesScreen.title
        Screen.ProfileScreen.route -> return Screen.ProfileScreen.title
    }

    return Screen.HomeScreen.title
}