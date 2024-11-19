package com.example.effectivemobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.effectivemobile.presentation.view.FavoritesView
import com.example.effectivemobile.presentation.view.HomeView
import com.example.effectivemobile.presentation.view.ProfileView
import com.example.effectivemobile.presentation.view.auth.AuthView
import com.example.effectivemobile.presentation.view.auth.RegistrationView
import com.example.effectivemobile.presentation.viewmodel.AuthViewModel
import com.example.effectivemobile.presentation.viewmodel.BookmarksViewModel
import com.example.effectivemobile.presentation.viewmodel.HomeViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    bookmarksViewModel: BookmarksViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {

        composable(route = Screen.RegistrationScreen.route) {
            RegistrationView(
                navController = navHostController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.AuthScreen.route) {
            AuthView(
                navController = navHostController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.HomeScreen.route) {
            HomeView(
                homeViewModel = homeViewModel,
                bookmarksViewModel = bookmarksViewModel
            )
        }

        composable(route = Screen.FavoritesScreen.route) {
            bookmarksViewModel.loadAllBookmarks()
            FavoritesView(
                bookmarksViewModel = bookmarksViewModel
            )
        }

        composable(route = Screen.ProfileScreen.route) {
            ProfileView()
        }
    }
}