package sw.swayni.rickandmorty

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import sw.swayni.rickandmorty.core.BottomNavigationItemModel
import sw.swayni.rickandmorty.core.NavigationKeys.Arg.CHARACTER_ID
import sw.swayni.rickandmorty.core.NavigationKeys.Route.DETAIL
import sw.swayni.rickandmorty.core.NavigationKeys.Route.FAVORITE
import sw.swayni.rickandmorty.core.NavigationKeys.Route.LIST
import sw.swayni.rickandmorty.ui.screen.detail.presentation.DetailScreen
import sw.swayni.rickandmorty.ui.screen.favorite_list.presentation.FavoriteListScreen
import sw.swayni.rickandmorty.ui.screen.list.presentatiton.ListScreen
import sw.swayni.rickandmorty.ui.theme.RickAndMortyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                RickAndMortyApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RickAndMortyApp(){
    val navController = rememberNavController()

    var isBottomExpanded by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { if (isBottomExpanded) {BottomNavigationView(navController)} },
        content = {
            NavHost(navController = navController, startDestination = "list") {
                composable(LIST) {
                    isBottomExpanded = true
                    ListScreen(
                        onNavigateToDetail = { character ->
                            navController.navigate(route = "detail/${character.id}")
                        }
                    )
                }
                composable(FAVORITE) {
                    isBottomExpanded = true
                    FavoriteListScreen(
                        onNavigateToDetail = { character ->
                            navController.navigate(route = "detail/${character.id}")
                        }
                    )
                }
                composable(
                    route = DETAIL,
                    arguments = listOf(navArgument(CHARACTER_ID) { type = NavType.IntType })
                ) {
                    isBottomExpanded = false
                    val characterId = it.arguments?.getInt(CHARACTER_ID) ?: 0
                    DetailScreen(id = characterId, navController = navController)
                }
            }
        }
    )
}

@Composable
fun BottomNavigationView(navController: NavController){
    val items = listOf(
        BottomNavigationItemModel(
            title = "List",
            icon = Icons.AutoMirrored.Filled.List,
            route = LIST
        ),
        BottomNavigationItemModel(
            title = "Favorite",
            icon = Icons.Default.Favorite,
            route = FAVORITE
        )
    )

    BottomNavigation (
        backgroundColor = colorResource(R.color.white),
        contentColor = colorResource(R.color.white)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title, tint = if (currentRoute == item.route) { colorResource(R.color.app_default_color) } else { Color.Gray })},
                label = { Text(text = item.title, color = if (currentRoute == item.route) { colorResource(R.color.app_default_color) } else { Color.Gray }, fontSize = 9.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f)
            )
        }
    }
}