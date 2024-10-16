package esi.mobg5.legocollec.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esi.mobg5.legocollec.data.LegoSetEntity
import esi.mobg5.legocollec.data.LegoSetRepository
import esi.mobg5.legocollec.model.LegoSet
import esi.mobg5.legocollec.repository.LegoSetRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the Lego set detail screen.
 * Handles data retrieval and operations for a specific Lego set.
 * @param apiRepository Repository for API operations.
 * @param localRepository Repository for local database operations.
 */
class DetailViewModel(
    private val apiRepository: LegoSetRepositoryApi,
    private val localRepository: LegoSetRepository
) : ViewModel() {

    // Function to get set details
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

    // Function to add set to collection
    fun addSetToCollection(legoSet: LegoSet, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                localRepository.addSetToCollection(legoSet)
                withContext(Dispatchers.Main) {
                    onError("Set added to collection")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.localizedMessage ?: "Failed to add set to collection")
                }
            }
        }
    }

    // Function to delete set from collection
    fun deleteSetFromCollection(legoSet: LegoSet, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val legoSetEntity = LegoSetEntity(
                    setNum = legoSet.set_num,
                    name = legoSet.name,
                    year = legoSet.year,
                    numParts = legoSet.num_parts,
                    setImageUrl = legoSet.set_img_url,
                )
                localRepository.delete(legoSetEntity)
                withContext(Dispatchers.Main) {
                    onError("Set deleted from collection")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.localizedMessage ?: "Failed to delete set from collection")
                }
            }
        }
    }

    // Function to check if set is in collection
    fun isSetInCollection(setNum: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isInCollection = localRepository.isSetInCollection(setNum)
            onResult(isInCollection)
        }
    }

}