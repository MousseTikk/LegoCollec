package esi.mobg5.legocollec.ui.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import esi.mobg5.legocollec.R
import esi.mobg5.legocollec.model.LegoSet

/**
 * Composable screen for displaying details of a specific Lego set.
 * @param navController Controller for navigation.
 * @param setNum The unique identifier of the Lego set.
 * @param detailViewModel ViewModel providing the data and operations for the detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegoSetDetailsScreen(navController: NavController, setNum: String, detailViewModel: DetailViewModel) {
    var legoSet by remember { mutableStateOf<LegoSet?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isInCollection by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(setNum) {
        detailViewModel.getSetDetails(setNum,
            onSuccess = { setDetails ->
                legoSet = setDetails
                isLoading = false
            },
            onError = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                loadError = true
                isLoading = false
            }
        )
        detailViewModel.isSetInCollection(setNum) { result ->
            isInCollection = result
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = legoSet?.name ?: stringResource(id = R.string.detail_screen_title)) },
                navigationIcon = {
                    if (!isLoading) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(bottom=60.dp)) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                if (isLoading) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                } else if (loadError) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(text = stringResource(id = R.string.error_loading_image))
                    }
                } else {
                    legoSet?.let { set ->
                        // Display Lego set details
                        Column(modifier = Modifier.padding(16.dp)) {
                            Image(
                                painter = rememberImagePainter(
                                    data = set.set_img_url,
                                    builder = {
                                        crossfade(true)
                                        placeholder(R.drawable.no_image_found)
                                        error(R.drawable.no_image_found)
                                    }
                                ),
                                contentDescription = stringResource(R.string.lego_set_image),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .padding(vertical = 16.dp),
                                alignment = Alignment.Center
                            )
                            Text(text = "${stringResource(R.string.set_number)} ${set.set_num}")
                            Text(text = "${stringResource(R.string.year)} ${set.year}")
                            Text(text = "${stringResource(R.string.number_of_parts)} ${set.num_parts}")
                            Spacer(modifier = Modifier.height(48.dp))
                            // Add or delete button based on collection status
                            Button(
                                onClick = {
                                    if (isInCollection) {
                                        showConfirmationDialog = true
                                    } else {
                                        detailViewModel.addSetToCollection(set) { errorMessage ->
                                            Toast.makeText(context, errorMessage, Toast

                                                .LENGTH_SHORT).show()
                                            isInCollection = true
                                        }
                                    }
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text(text = if (isInCollection) stringResource(R.string.delete_from_collection) else stringResource(R.string.add_to_collection))
                            }
                            // Confirmation dialog for deletion
                            if (showConfirmationDialog) {
                                AlertDialog(
                                    onDismissRequest = {
                                        showConfirmationDialog = false
                                    },
                                    title = {
                                        Text(stringResource(R.string.confirm_deletion))
                                    },
                                    text = {
                                        Text(stringResource(R.string.confirm_deletion_message))
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                detailViewModel.deleteSetFromCollection(set) { errorMessage ->
                                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                                    isInCollection = false
                                                }
                                                showConfirmationDialog = false
                                            }
                                        ) {
                                            Text(stringResource(R.string.yes))
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(
                                            onClick = {
                                                showConfirmationDialog = false
                                            }
                                        ) {
                                            Text(stringResource(R.string.no))
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}