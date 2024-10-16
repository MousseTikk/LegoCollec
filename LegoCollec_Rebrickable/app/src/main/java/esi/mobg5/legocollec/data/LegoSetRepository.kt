package esi.mobg5.legocollec.data
import esi.mobg5.legocollec.model.LegoSet
import kotlinx.coroutines.flow.Flow
/**
 * Repository for handling the data operations of LegoSets.
 * Provides a clean API to the rest of the app for app data.
 */
class LegoSetRepository(private val legoSetDao: LegoSetDao) {
    /**
     * Flow of all LegoSets in the database.
     */
    val allSets: Flow<List<LegoSetEntity>> = legoSetDao.getAllLegoSets()

    /**
     * Inserts a LegoSetEntity into the database.
     * @param legoSet The LegoSetEntity to be inserted.
     */
    suspend fun insert(legoSet: LegoSetEntity) {
        legoSetDao.insertLegoSet(legoSet)
    }

    /**
     * Deletes a LegoSetEntity from the database.
     * @param legoSet The LegoSetEntity to be deleted.
     */
    suspend fun delete(legoSet: LegoSetEntity) {
        legoSetDao.deleteLegoSet(legoSet)
    }
    /**
     * Adds a new LegoSet to the collection in the database.
     * Converts a model LegoSet to a LegoSetEntity and inserts it.
     * @param legoSet The LegoSet model to be added.
     */
    suspend fun addSetToCollection(legoSet: LegoSet) {
            val legoSetEntity = LegoSetEntity(
                setNum = legoSet.set_num,
                name = legoSet.name,
                year = legoSet.year,
                numParts = legoSet.num_parts,
                setImageUrl = legoSet.set_img_url
            )
            insert(legoSetEntity)
        }
    /**
     * Checks if a LegoSet is already in the collection.
     * @param setNum The set number of the LegoSet to check.
     * @return True if the set is in the collection, false otherwise.
     */
    suspend fun isSetInCollection(setNum: String): Boolean {
        return legoSetDao.getLegoSet(setNum) != null
    }

}

