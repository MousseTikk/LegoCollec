package esi.mobg5.legocollec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import esi.mobg5.legocollec.data.LegoSetDatabase
import esi.mobg5.legocollec.data.LegoSetRepository
import esi.mobg5.legocollec.network.RebrickableRetrofitInstance
import esi.mobg5.legocollec.network.RebrickableService
import esi.mobg5.legocollec.repository.LegoSetRepositoryApi
import esi.mobg5.legocollec.ui.theme.LegoCollecTheme

/**
 * Main activity for the Lego collection application.
 * Initializes the database, repositories, and sets the content view.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create or open the local database for storing Lego set data.
        val database = Room.databaseBuilder(
            applicationContext,
            LegoSetDatabase::class.java, "lego-database"
        ).build()

        // Initialize the DAO and repositories for local and API data.
        val legoSetDao = database.legoSetDao()
        val legoSetRepository = LegoSetRepository(legoSetDao)
        val rebrickableService = RebrickableRetrofitInstance.rebrickableRetrofit.create(RebrickableService::class.java)
        val apiRepository = LegoSetRepositoryApi(rebrickableService)

        // Set the content view with Compose UI.
        setContent {
            LegoCollecTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Start the main application composable.
                    LegoApp(rememberNavController(), legoSetRepository, apiRepository)
                }
            }
        }
    }
}