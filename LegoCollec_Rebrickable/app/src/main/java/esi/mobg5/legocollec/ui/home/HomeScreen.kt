package esi.mobg5.legocollec.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import esi.mobg5.legocollec.R
import esi.mobg5.legocollec.model.LegoSet
import esi.mobg5.legocollec.ui.navigation.NavigationDestination
import esi.mobg5.legocollec.ui.theme.LegoCollecTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
    override val icon = Icons.Filled.Home
}


/**
 * Composable function for the Home screen of the application.
 * Displays a grid of Lego sets and a sort order toggle button.
 * @param navController Navigation controller for screen navigation.
 * @param viewModel ViewModel that provides data and functionalities for the Home screen.
 */
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val sortOrderText by viewModel.sortOrderText.collectAsState()
    val pagingDataFlow = viewModel.legoSets.collectAsState().value
    val legoSets = pagingDataFlow.collectAsLazyPagingItems()

    LegoCollecTheme {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Toggle button for sorting order
            Button(onClick = { viewModel.toggleSortOrder() }) {
                Text(text = sortOrderText)
            }
            // Grid display for Lego sets
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(legoSets.itemCount) { index ->
                    legoSets[index]?.let { legoSet ->
                        LegoSetItem(legoSet) { selectedLegoSet ->
                            navController.navigate("legoSetDetails/${selectedLegoSet.set_num}")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display a single Lego set item.
 * @param legoSet LegoSet data to be displayed.
 * @param navigateToDetails Function to navigate to the details screen of the Lego set.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun LegoSetItem(legoSet: LegoSet, navigateToDetails: (LegoSet) -> Unit) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { navigateToDetails(legoSet) }
    ) {
        val imagePainter = rememberImagePainter(
            data = legoSet.set_img_url,
            builder = {
                crossfade(true)
                placeholder(R.drawable.no_image_found)
            }
        )
        Image(
            painter = imagePainter,
            contentDescription = "${legoSet.name} image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // Display progress indicator or error text based on image loading state
        when (imagePainter.state) {
            is ImagePainter.State.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            is ImagePainter.State.Error -> {
                Text(
                    text = legoSet.name,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {}
        }
    }

}