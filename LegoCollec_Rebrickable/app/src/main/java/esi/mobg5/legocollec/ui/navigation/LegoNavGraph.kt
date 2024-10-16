package esi.mobg5.legocollec.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import esi.mobg5.legocollec.data.LegoSetRepository
import esi.mobg5.legocollec.repository.LegoSetRepositoryApi
import esi.mobg5.legocollec.ui.collection.CollectionDestination
import esi.mobg5.legocollec.ui.collection.CollectionScreen
import esi.mobg5.legocollec.ui.collection.CollectionViewModel
import esi.mobg5.legocollec.ui.detail.DetailViewModel
import esi.mobg5.legocollec.ui.detail.LegoSetDetailsScreen
import esi.mobg5.legocollec.ui.home.HomeDestination
import esi.mobg5.legocollec.ui.home.HomeScreen
import esi.mobg5.legocollec.ui.home.HomeViewModel
import esi.mobg5.legocollec.ui.search.SearchDestination
import esi.mobg5.legocollec.ui.search.SearchScreen
import esi.mobg5.legocollec.ui.search.SearchViewModel

/**
 * Composable function that defines the navigation graph for the application.
 * @param navController Controller for managing app navigation.
 * @param legoSetRepository Repository for local Lego set data operations.
 * @param apiRepository Repository for remote Lego set data operations.
 */
@Composable
fun LegoNavHost(
    navController: NavHostController,
    legoSetRepository: LegoSetRepository,
    apiRepository: LegoSetRepositoryApi
) {
    // Define the navigation host with the starting destination.
    NavHost(navController = navController, startDestination = HomeDestination.route) {
        // Instantiate ViewModels for different screens.
        val homeViewModel = HomeViewModel()
        val collectionViewModel = CollectionViewModel(legoSetRepository)
        val detailViewModel = DetailViewModel(apiRepository, legoSetRepository)

        // Define composable destinations.
        composable(HomeDestination.route) {
            HomeScreen(navController, homeViewModel)
        }
        composable(SearchDestination.route) {
            val searchViewModel = SearchViewModel(apiRepository)
            SearchScreen(navController, searchViewModel)
        }
        composable(CollectionDestination.route) {
            CollectionScreen(collectionViewModel)
        }
        composable("legoSetDetails/{setNum}", arguments = listOf(navArgument("setNum") { type = NavType.StringType })) { backStackEntry ->
            val setNum = backStackEntry.arguments?.getString("setNum") ?: return@composable
            LegoSetDetailsScreen(navController, setNum, detailViewModel)
        }
    }
}