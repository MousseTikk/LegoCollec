package esi.mobg5.legocollec.model
import kotlinx.serialization.Serializable


/**
 * Data class representing a Lego set.
 * Contains detailed information about a specific Lego set.
 */
@Serializable
data class LegoSet(
    val set_num: String,
    val name: String,
    val year: Int,
    val num_parts: Int,
    val set_img_url: String,
    val set_url: String,
    val last_modified_dt: String
)
/**
 * Data class for the response from the API containing a list of Lego sets.
 * Used to parse the JSON response from the API.
 */
@Serializable
data class LegoSetsResponse(
    val count: Int,
    val results: List<LegoSet>
)
