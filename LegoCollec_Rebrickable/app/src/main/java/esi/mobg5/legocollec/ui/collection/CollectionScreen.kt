package esi.mobg5.legocollec.ui.collection

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import esi.mobg5.legocollec.R
import esi.mobg5.legocollec.data.LegoSetEntity
import esi.mobg5.legocollec.ui.navigation.NavigationDestination

object CollectionDestination : NavigationDestination {
    override val route = "collection"
    override val titleRes = R.string.collection_title
    override val icon = Icons.Filled.List
}
/**
 * Composable screen for displaying the collection of Lego sets.
 * @param viewModel ViewModel providing the data and operations for the collection.
 */
@Composable
fun CollectionScreen(viewModel: CollectionViewModel) {
    val context = LocalContext.current
    val legoSets by viewModel.legoSets.collectAsState(initial = emptyList())
    // Use a column layout to display the title and the list of Lego sets.
    Column {
        // Title
        Text(
            text = stringResource(id = R.string.collection_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        // Display the list of Lego sets or a message if the collection is empty.
        if (legoSets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.empty_collection),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            // Use LazyColumn for efficient loading of list items.
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(legoSets) { legoSet ->
                    LegoSetCard(legoSet, onDelete = {
                        viewModel.deleteLegoSet(legoSet) { errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                item {
                    // Spacer item at the end of the list to ensure it can scroll above the NavBar
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}
/**
 * Composable card representing a single Lego set.
 * @param legoSet The LegoSetEntity to display.
 * @param onDelete Callback function triggered when delete icon is clicked.
 */
@Composable
fun LegoSetCard(legoSet: LegoSetEntity, onDelete: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberImagePainter(legoSet.setImageUrl),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)) {
                Text(text = "${stringResource(R.string.set_name)} ${legoSet.name}")
                Text(text = "${stringResource(R.string.set_number)} ${legoSet.setNum}")
                Text(text = "${stringResource(R.string.year)} ${legoSet.year}")
                Text(text = "${stringResource(R.string.number_of_parts)} ${legoSet.numParts}")

            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription =stringResource(id = R.string.delete) )
            }
        }
    }
}