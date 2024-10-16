package esi.mobg5.legocollec.ui.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import esi.mobg5.legocollec.R
import esi.mobg5.legocollec.ui.navigation.NavigationDestination

object SearchDestination : NavigationDestination {
    override val route = "search"
    override val titleRes = R.string.search_title
    override val icon = Icons.Filled.Search
}

/**
 * Composable function for the search screen in the application.
 * Allows users to search for Lego sets by their set number.
 * @param navController Navigation controller for navigating between screens.
 * @param searchViewModel ViewModel to handle search operations.
 */
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var setFound by remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Column layout for search input and results
    Column(modifier = Modifier.fillMaxWidth()) {
        // TextField for inputting search query
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(id = R.string.enter_lego_set_number)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                isLoading = true
                setFound = true
                searchViewModel.getSetDetails(searchQuery,
                    onSuccess = { legoSet ->
                        isLoading = false
                        navController.navigate("legoSetDetails/${legoSet.set_num}")
                    },
                    onError = { errorMessage ->
                        isLoading = false
                        setFound = false
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            })
        )

        // Show progress indicator or image based on search state
        when {
            isLoading -> CircularProgressIndicator()
            !setFound -> Image(
                painter = painterResource(id = R.drawable.set_not_found),
                contentDescription = stringResource(R.string.set_not_found),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}