package esi.mobg5.legocollec.data
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room database for this app.
 * Contains the database holder and serves as the main access point
 * for the underlying connection to the app's persisted data.
 */
@Database(entities = [LegoSetEntity::class], version = 1, exportSchema = false)
abstract class LegoSetDatabase : RoomDatabase() {
    /**
     * Access method for the LegoSetDao.
     * @return An instance of LegoSetDao.
     */
    abstract fun legoSetDao(): LegoSetDao
}