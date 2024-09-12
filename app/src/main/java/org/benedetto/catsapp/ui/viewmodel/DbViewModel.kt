package org.benedetto.catsapp.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.benedetto.data.repository.local.FavoriteCatsRepository
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(private val repository: FavoriteCatsRepository) : ViewModel() {

    private val _favoriteCatIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteCatIds: StateFlow<List<String>> = _favoriteCatIds

    init {
        // Load all favorite cats initially
        loadFavoriteCats()
    }

    fun loadFavoriteCats() {
        viewModelScope.launch {
            val favorites = repository.getFavoriteCatIds()
            _favoriteCatIds.value = favorites
        }
    }

    fun addToFavorites(catId: String) {
        viewModelScope.launch {
            repository.addCatToFavorites(catId)
            loadFavoriteCats() // Reload favorites after adding
        }
    }

    fun isCatFavorite(catId: String): Boolean {
        return _favoriteCatIds.value.contains(catId)
    }

    fun toggleFavorite(catId: String) {
        viewModelScope.launch {
            if (_favoriteCatIds.value.contains(catId)) {
                // If it's already a favorite, remove it
                repository.removeCatFromFavorites(catId)
            } else {
                // Otherwise, add it to favorites
                repository.addCatToFavorites(catId)
            }
            loadFavoriteCats() // Reload favorites after toggling
        }
    }
}