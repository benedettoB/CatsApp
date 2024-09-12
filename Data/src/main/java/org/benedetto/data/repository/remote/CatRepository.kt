package org.benedetto.data.repository.remote

import kotlinx.coroutines.flow.Flow
import org.benedetto.data.model.Cat

interface CatRepository {
    fun fetchCats(): Flow<List<Cat>>
}