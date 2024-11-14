package com.example.effectivemobile

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.effectivemobile.presentation.config.bottomMenuRoutes
import com.example.effectivemobile.presentation.navigation.NavigationGraph
import com.example.effectivemobile.presentation.navigation.Screen
import com.example.effectivemobile.ui.theme.Background
import com.example.effectivemobile.ui.theme.EffectiveMobileTheme
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

            EffectiveMobileTheme {

                val navController = rememberNavController()

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
                            NavigationGraph(navHostController = navController)
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