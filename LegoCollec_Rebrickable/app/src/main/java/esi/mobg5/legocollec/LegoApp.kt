package esi.mobg5.legocollec

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import esi.mobg5.legocollec.data.LegoSetRepository
import esi.mobg5.legocollec.repository.LegoSetRepositoryApi
import esi.mobg5.legocollec.ui.collection.CollectionDestination
import esi.mobg5.legocollec.ui.home.HomeDestination
import esi.mobg5.legocollec.ui.navigation.LegoNavHost
import esi.mobg5.legocollec.ui.search.SearchDestination

/**
 * Top level composable function for the Lego collection application.
 * Sets up the navigation and the bottom bar for switching between different screens.
 * @param navController Controls the navigation between composables.
 * @param legoSetRepository Repository for accessing local database operations.
 * @param apiRepository Repository for accessing API operations.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LegoApp(navController: NavHostController = rememberNavController(), legoSetRepository: LegoSetRepository, apiRepository: LegoSetRepositoryApi) {
    // Define the items for the bottom navigation bar.
    val items = listOf(HomeDestination, SearchDestination, CollectionDestination)

    // Scaffold for the basic material design layout structure.
    Scaffold(
        bottomBar = {
            // Setup the bottom navigation bar.
            NavigationBar {
                val currentRoute = currentRoute(navController)
                items.forEach { destination ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = destination.icon, contentDescription = null) },
                        selected = currentRoute == destination.route,
                        onClick = {
                            // Navigation action for each item.
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        // Define the navigation host for managing composable screens.
        LegoNavHost(navController = navController, legoSetRepository = legoSetRepository, apiRepository = apiRepository)
    }
}

/**
 * Helper composable to get the current route from the navigation controller.
 * @param navController The NavController from which to get the current route.
 * @return The route of the current destination.
 */
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}