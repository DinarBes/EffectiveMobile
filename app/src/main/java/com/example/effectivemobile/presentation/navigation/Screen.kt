package com.example.effectivemobile.presentation.navigation

sealed class Screen(val route: String, val title: String) {

    data object RegistrationScreen: Screen(route = "registration_screen", title = "")

    data object AuthScreen: Screen(route = "auth_screen", title = "")

    data object HomeScreen: Screen(route = "home_screen", title = "")

    data object FavoritesScreen: Screen(route = "favorites_screen", title = "Избранное")

    data object ProfileScreen: Screen(route = "profile_screen", title = "Профиль")
}