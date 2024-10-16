package esi.mobg5.legocollec.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a Lego set.
 * This class defines the schema of a table in the Room database
 * for storing Lego set data.
 */
@Entity(tableName = "lego_set_table")
data class LegoSetEntity(
    @PrimaryKey val setNum: String,
    val name: String,
    val year: Int,
    val numParts: Int,
    val setImageUrl: String
)