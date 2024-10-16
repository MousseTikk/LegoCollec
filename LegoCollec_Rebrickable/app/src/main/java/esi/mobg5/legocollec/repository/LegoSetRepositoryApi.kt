package esi.mobg5.legocollec.repository

import esi.mobg5.legocollec.model.LegoSet
import esi.mobg5.legocollec.network.RebrickableService
/**
 * Repository class for handling API operations related to Lego sets.
 * Provides methods for fetching details of specific Lego sets.
 */
class LegoSetRepositoryApi(private val service: RebrickableService) {
    /**
     * Fetches the details of a specific Lego set.
     * @param setNum The unique identifier of the Lego set.
     * @return The details of the Lego set or null in case of failure.
     */
    suspend fun getSetDetails(setNum: String): LegoSet? {
        return try {
            val response = service.getSetDetails(setNum)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}