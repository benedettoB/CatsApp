package org.benedetto.catsapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.benedetto.data.model.Cat
import org.benedetto.data.remote.CatRepository
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val catRepository: CatRepository) : ViewModel() {

    private val _catState = MutableStateFlow<List<Cat>>(emptyList())
    val catState: StateFlow<List<Cat>> = _catState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    // Fetch cat data from repository
    fun fetchCats() {
        viewModelScope.launch {
            catRepository.fetchCats() // Calls the repository (CatRepositoryImpl through Hilt)
                .catch { exception ->
                    _errorState.value = exception.message
                }
                .collect { cats ->
                    _catState.value += cats
                }
        }
    }


}
// Data state classes
sealed class UiState {
    object Loading : UiState()
    data class Success(val cats: Array<Cat>) : UiState()  // The data could be any type, like a model

    data class Error(val message: String) : UiState()
}

/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    // Mutable state that holds the data fetched from a remote repository
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    // Fetch data from a remote repository (simulated)
    fun fetchDetails() {
        viewModelScope.launch {
            try {
                // Simulate fetching data from a repository
                val result = remoteRepository.fetchDetails()
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to fetch details")
            }
        }
    }
}

// Data state classes
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()  // The data could be any type, like a model
    data class Error(val message: String) : UiState()
}

 */
/*
class CatViewModel @Inject constructor(
    private val catRepository: CatRepository // Injecting the interface, not the implementation
) : ViewModel() {

    private val _catState = MutableStateFlow<List<Cat>>(emptyList())
    val catState: StateFlow<List<Cat>> = _catState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    // Fetch cat data from repository
    fun fetchCats() {
        viewModelScope.launch {
            catRepository.fetchCats() // Calls the repository (CatRepositoryImpl through Hilt)
                .catch { exception ->
                    _errorState.value = exception.message
                }
                .collect { cats ->
                    _catState.value += cats
                }
        }
    }

    // Data state classes
    sealed class UiState {
        object Loading : UiState()
        data class Success(val cats: Array<Cat> ) : UiState()  // The data could be any type, like a model
        data class Error(val message: String) : UiState()
    }
}

 */