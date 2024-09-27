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
import org.benedetto.data.model.Cat
import org.benedetto.data.repository.remote.CatRepository
import org.benedetto.data.util.log
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class CatViewModel @Inject constructor(private val catRepository: CatRepository) : ViewModel() {

    private val _catState = MutableStateFlow<List<Cat>>(emptyList())
    val catState: StateFlow<List<Cat>> = _catState.asStateFlow()

    fun fetchCats() {
        val time = measureTimeMillis {
        log("fetchCats() called from UI: main thread")
        viewModelScope.launch {
            log("fetchCats() inside viewModelScope: main thread")
            catRepository.fetchCats()
                .catch { exception ->
                    Log.e("CatViewModel", exception.message, exception)
                }
                .buffer() //buffer emissions don't wait
                //collect is called on main thread
                .collect { cats ->
                    log("collect called on flow : main thread")
                    _catState.value += cats
                }
            }
        }
        log("Collected in $time")
    }
}
/*
milliseconds without buffer: 8
milliseconds with buffer: 1
We can use a buffer operator on a flow to run emitting code of the simple flow concurrently with collecting code,
as opposed to running them sequentially
 */