package esi.mobg5.legocollec.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
/**
 * Interface representing a navigation destination within the app.
 * Defines the route, title resource, and icon for each destination.
 */
interface NavigationDestination {
    val route: String          // Unique route name for the navigation destination.
    val titleRes: Int          // Resource ID for the title of the destination.
    val icon: ImageVector      // Icon representing the destination.
}
