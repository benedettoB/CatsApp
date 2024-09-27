package org.benedetto.catsapp.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.benedetto.data.repository.local.FavoriteCatsRepository
import org.benedetto.data.util.log
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@HiltViewModel
class DbViewModel @Inject constructor(private val repository: FavoriteCatsRepository) : ViewModel() {

    private val _favoriteCatIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteCatIds: StateFlow<List<String>> = _favoriteCatIds.asStateFlow()

    init {
        loadFavoriteCats()
    }
    fun loadFavoriteCats(){
        val time = measureTimeMillis {
            log("loadFavoriteCats() called from UI: main thread")
            viewModelScope.launch {
                repository.getFavoriteCats()
                    .catch { exception ->
                        Log.e("DbViewModel", exception.message, exception)
                    }
                    .buffer()
                    .collect { values ->
                        log("collect called on flow : main thread")
                        _favoriteCatIds.value = values
                    }
            }
        }
        log("Collected in $time")
    }

    fun toggleFavorite(catId: String) {
        viewModelScope.launch {
            if (isCatFavorite(catId)) {
                // If it's already a favorite, remove it
                repository.removeCatFromFavorites(catId)
            } else {
                // Otherwise, add it to favorites
                repository.addCatToFavorites(catId)
            }
            loadFavoriteCats() // Reload favorites after toggling
        }
    }

    private fun isCatFavorite(catId: String): Boolean {
        return _favoriteCatIds.value.contains(catId)
    }
}

/*
    /*
    using asStateFlow(): provides a read-only view of the mutable flow to external observers
       this approach enforces encapsulation: external components can observe favoriteCatIds but cannot directly modify its contents.

     */
    @ActivityRetainedScoped
        Scope: Retained across the lifecycle of an activity but survives configuration changes (like screen rotation)
        Lifecycle: Exists from when the activity is created until it is destroyed permanently (no recreated)
        Usage: For dependencies that need to be retained across activity configuration changes, like ViewModels.
 */
/*
Persistence
ViewModel allows persistence through both the state that a ViewModel holds, and the operations that a ViewModel triggers. This caching means that you don’t have to fetch data again through common configuration changes, such as a screen rotation

The most important thing to keep in mind when using ViewModel with Compose is that you cannot scope a ViewModel to a composable.
To get the benefits of ViewModel in Compose, host each screen in a Fragment or Activity, or use Compose Navigation and use ViewModels in
composable functions as close as possible to the Navigation destination.
 */
/*
    @HiltViewModel key features;
        1. Automatic ViwModel Injection: no need to provide ViewModel instances using factories, hilt generates it for you.
        2. ViewModel Scope: view models are scoped to their respective lifecycle owners (either Activity or Fragment); this ensures the
                            injected dependncies live as long as the ViewModel does.

    @ActivityRetainedScoped key features:
       Dependencies are resused across all ViewModels within the activity, reducing the overhead of multiple instances
       Don't combine with @HiltViewModel unless dependcies are being injected into multiple view models

 */