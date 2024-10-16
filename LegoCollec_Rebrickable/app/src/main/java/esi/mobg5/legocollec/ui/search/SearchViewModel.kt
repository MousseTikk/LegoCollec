package esi.mobg5.legocollec.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esi.mobg5.legocollec.model.LegoSet
import esi.mobg5.legocollec.repository.LegoSetRepositoryApi
import kotlinx.coroutines.launch

/**
 * ViewModel for the search screen.
 * Handles the logic for searching Lego sets by their set number.
 * @param apiRepository Repository for handling API requests.
 */
class SearchViewModel(private val apiRepository: LegoSetRepositoryApi) : ViewModel() {
    /**
     * Fetches details of a Lego set based on the set number.
     * @param setNum The set number to search for.
     * @param onSuccess Callback for when details are successfully fetched.
     * @param onError Callback for when an error occurs during fetching.
     */
    fun getSetDetails(setNum: String, onSuccess: (LegoSet) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiRepository.getSetDetails(setNum)
                if (response != null) {
                    onSuccess(response)
                } else {
                    onError("Set not found")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error occurred")
            }
        }
    }
}