package esi.mobg5.legocollec.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for accessing LegoSet data in the database.
 * Provides methods for inserting, deleting, and querying LegoSet entities.
 */
@Dao
interface LegoSetDao {
    /**
     * Inserts a new LegoSetEntity into the database.
     * If the LegoSetEntity already exists, it replaces the existing one.
     * @param legoSet The LegoSetEntity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLegoSet(legoSet: LegoSetEntity)

    /**
     * Deletes a LegoSetEntity from the database.
     * @param legoSet The LegoSetEntity to be deleted.
     */
    @Delete
    suspend fun deleteLegoSet(legoSet: LegoSetEntity)

    /**
     * Retrieves all LegoSet entities from the database.
     * @return A Flow emitting the list of LegoSetEntities.
     */
    @Query("SELECT * FROM lego_set_table")
    fun getAllLegoSets(): Flow<List<LegoSetEntity>>

    /**
     * Retrieves a single LegoSetEntity by its set number.
     * @param setNum The set number of the LegoSetEntity to retrieve.
     * @return The LegoSetEntity or null if not found.
     */
    @Query("SELECT * FROM lego_set_table WHERE setNum = :setNum")
    suspend fun getLegoSet(setNum: String): LegoSetEntity?
}