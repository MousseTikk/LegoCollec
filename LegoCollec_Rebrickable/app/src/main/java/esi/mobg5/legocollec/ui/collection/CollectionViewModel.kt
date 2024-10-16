package esi.mobg5.legocollec.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esi.mobg5.legocollec.data.LegoSetEntity
import esi.mobg5.legocollec.data.LegoSetRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for the Collection screen.
 * Handles data retrieval and operations for the Lego sets collection.
 * @param repository The repository to interact with LegoSet data.
 */
class CollectionViewModel(private val repository: LegoSetRepository) : ViewModel() {
    val legoSets = repository.allSets

    /**
     * Deletes a LegoSet from the collection.
     * @param legoSet The LegoSetEntity to be deleted.
     * @param onError Callback function to be invoked in case of error.
     */
    fun deleteLegoSet(legoSet: LegoSetEntity, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {                repository.delete(legoSet)
            } catch (e: Exception) {
                //lambda function that provides already a toast
                onError(e.localizedMessage ?: "Error occurred")
            }
        }
    }
}