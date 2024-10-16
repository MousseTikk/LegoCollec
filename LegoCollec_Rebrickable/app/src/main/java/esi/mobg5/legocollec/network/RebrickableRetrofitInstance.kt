package esi.mobg5.legocollec.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Singleton object to create and provide a Retrofit instance.
 * Used for network operations related to the Rebrickable API.
 */
object RebrickableRetrofitInstance {
    private const val REBRICKABLE_BASE_URL = "https://rebrickable.com/api/v3/"
    // Lazy initialization of Retrofit instance with Gson converter
    val rebrickableRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(REBRICKABLE_BASE_URL)
            .build()
    }
}