package com.example.effectivemobile

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.effectivemobile.presentation.config.bottomMenuRoutes
import com.example.effectivemobile.presentation.config.getLabelForRoute
import com.example.effectivemobile.presentation.config.topBarRoutes
import com.example.effectivemobile.presentation.navigation.NavigationGraph
import com.example.effectivemobile.presentation.navigation.Screen
import com.example.effectivemobile.presentation.viewmodel.AuthViewModel
import com.example.effectivemobile.presentation.viewmodel.BookmarksViewModel
import com.example.effectivemobile.presentation.viewmodel.HomeViewModel
import com.example.effectivemobile.presentation.viewmodel.SearchViewModel
import com.example.effectivemobile.ui.theme.Background
import com.example.effectivemobile.ui.theme.EffectiveMobileTheme
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            val navController = rememberNavController()
            val authViewModel: AuthViewModel = hiltViewModel<AuthViewModel>()
            val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
            val bookmarksViewModel: BookmarksViewModel = hiltViewModel<BookmarksViewModel>()
            val searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>()

            EffectiveMobileTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background
                ) {

                    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                    val bottomBarItems = listOf(
                        BottomNavigationItem(
                            route = Screen.HomeScreen.route,
                            name =  stringResource(id = R.string.home),
                            painter = painterResource(id = R.drawable.house),
                            tint = LightGrey
                        ),
                        BottomNavigationItem(
                            route = Screen.FavoritesScreen.route,
                            name = stringResource(id = R.string.favorites),
                            painter = painterResource(id = R.drawable.bookmark),
                            tint = LightGrey
                        ),
                        BottomNavigationItem(
                            route = Screen.ProfileScreen.route,
                            name = stringResource(id = R.string.account),
                            painter = painterResource(id = R.drawable.person),
                            tint = LightGrey
                        )
                    )

                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }

                    Scaffold(
                        topBar = {
                            topBarRoutes.forEach { topBarRoute ->
                                if (topBarRoute == currentRoute) {
                                    TopAppBar(title = {
                                        Text(
                                            text = getLabelForRoute(topBarRoute),
                                            color = LightGrey,
                                            fontFamily = Roboto,
                                            fontWeight = FontWeight.W400,
                                            fontSize = 22.sp
                                        )
                                    }
                                    )
                                }
                            }
                        },
                        bottomBar = {
                            bottomMenuRoutes.forEach { bottomMenuRoute ->
                                if (bottomMenuRoute == currentRoute) {
                                    NavigationBar {
                                        bottomBarItems.forEachIndexed { index, bottomNavigationItem ->
                                            NavigationBarItem(
                                                selected = selectedItemIndex == index,
                                                onClick = {
                                                    selectedItemIndex = index
                                                    navController.navigate(route = bottomNavigationItem.route)
                                                },
                                                icon = {
                                                    Icon(
                                                        painter = bottomNavigationItem.painter,
                                                        tint = if (bottomNavigationItem.route == currentRoute) Green else LightGrey,
                                                        contentDescription = null
                                                    )
                                                },
                                                label = {
                                                    Text(
                                                        text = bottomNavigationItem.name,
                                                        fontFamily = Roboto,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.W600,
                                                        color = if (bottomNavigationItem.route == currentRoute) Green else LightGrey
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(modifier = Modifier.padding(it)) {
                            NavigationGraph(
                                navHostController = navController,
                                authViewModel = authViewModel,
                                homeViewModel = homeViewModel,
                                bookmarksViewModel = bookmarksViewModel,
                                searchViewModel = searchViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

data class BottomNavigationItem(
    val route: String,
    val name: String,
    val painter: Painter,
    val tint: Color
)