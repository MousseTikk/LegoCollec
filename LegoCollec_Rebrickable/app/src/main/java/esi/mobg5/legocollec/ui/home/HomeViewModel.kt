package esi.mobg5.legocollec.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import esi.mobg5.legocollec.model.LegoSet
import esi.mobg5.legocollec.network.RebrickableRetrofitInstance
import esi.mobg5.legocollec.network.RebrickableService
import esi.mobg5.legocollec.paging.LegoSetPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the Home screen.
 * Manages the loading and sorting of Lego sets.
 */
class HomeViewModel : ViewModel() {
    private val service = RebrickableRetrofitInstance.rebrickableRetrofit.create(RebrickableService::class.java)
    private var isDescendingSort = true
    private val _sortOrderText = MutableStateFlow("Sort Descending")
    val sortOrderText: StateFlow<String> = _sortOrderText

    private val _legoSets = MutableStateFlow(createPager())
    val legoSets: StateFlow<Flow<PagingData<LegoSet>>> = _legoSets

    /**
     * Toggles the sort order between ascending and descending.
     */
    fun toggleSortOrder() {
        isDescendingSort = !isDescendingSort
        _sortOrderText.update { if (isDescendingSort) "Sort Descending" else "Sort Ascending" }
        _legoSets.value = createPager()
    }

    /**
     * Creates a Pager for paging data of Lego sets.
     */
    private fun createPager(): Flow<PagingData<LegoSet>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { LegoSetPagingSource(service, getOrder()) }
        ).flow.cachedIn(viewModelScope)
    }

    /**
     * Returns the sorting order string based on the current state.
     */
    private fun getOrder() = if (isDescendingSort) "-year,-month,-day" else "year,month,day"
}