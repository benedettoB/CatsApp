package org.benedetto.data.repository.remote

import kotlinx.coroutines.flow.Flow
import org.benedetto.data.model.Cat

interface CatRepository {
    fun fetchCats(): Flow<List<Cat>>
}
/*
 Flow is cold stream, meaning it doesn't produce or emit data until is collected.
 Every time you collect a Flow, it starts from the beginning and runs the data-producing code again.

 This is different from a hot stream like StateFlow, which always holds the latest value and keeps emitting updates
 regardless of whether it's being collected
 */