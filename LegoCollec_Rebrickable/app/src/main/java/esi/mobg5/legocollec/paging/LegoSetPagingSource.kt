package esi.mobg5.legocollec.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import esi.mobg5.legocollec.model.LegoSet
import esi.mobg5.legocollec.network.RebrickableService
import retrofit2.HttpException
import java.io.IOException
/**
 * PagingSource for fetching Lego sets from the Rebrickable API.
 * Used with the Paging 3 library to load data incrementally.
 */
class LegoSetPagingSource(
    private val service: RebrickableService,
    private val ordering: String
) : PagingSource<Int, LegoSet>() {
    /**
     * Loads a single page of results.
     * @param params Information about the load operation, including the page number.
     * @return The load result containing either a page of items or an error.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LegoSet> {
        val page = params.key ?: 1
        return try {
            val response = service.getSets(page, ordering, params.loadSize)
            val legoSets = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = legoSets,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (legoSets.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
    /**
     * Gets the key for the closest page to the one that was most recently accessed. No used here.
     * @param state Current paging state.
     * @return The page key or null if not found.
     */
    override fun getRefreshKey(state: PagingState<Int, LegoSet>): Int? {
        return null
    }
}