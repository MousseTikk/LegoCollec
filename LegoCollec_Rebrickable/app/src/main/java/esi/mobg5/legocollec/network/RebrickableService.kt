package esi.mobg5.legocollec.network

import esi.mobg5.legocollec.model.LegoSet
import esi.mobg5.legocollec.model.LegoSetsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
/**
 * Retrofit service interface for Rebrickable API.
 * Contains methods for fetching Lego set data.
 */
interface RebrickableService {
    /**
     * Fetches a list of Lego sets from the API.
     * @param page Page number for pagination.
     * @param ordering Ordering criteria for the results.
     * @param pageSize Number of items per page.
     * @return Response containing a list of Lego sets.
     */
    @Headers("Authorization: key a7b4c0055adf2b3851825adc731d55d4")
    @GET("lego/sets/")
    suspend fun getSets(
        @Query("page") page: Int,
        @Query("ordering") ordering: String,
        @Query("page_size") pageSize: Int
    ): Response<LegoSetsResponse>
    /**
     * Fetches details of a single Lego set.
     * @param setNum Unique identifier of the Lego set.
     * @return Response containing the details of the Lego set.
     */
    @Headers("Authorization: key a7b4c0055adf2b3851825adc731d55d4")
    @GET("lego/sets/{set_num}/")
    suspend fun getSetDetails(@Path("set_num") setNum: String): Response<LegoSet>

}