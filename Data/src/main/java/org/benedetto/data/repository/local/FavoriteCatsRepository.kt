package org.benedetto.data.repository.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.benedetto.data.model.FavoriteCat
import org.benedetto.data.util.log
import javax.inject.Inject

class FavoriteCatsRepository @Inject constructor(private val favoriteCatDao: FavoriteCatDao) {

    suspend fun addCatToFavorites(catId: String) {
        withContext(Dispatchers.IO){
            val existingCat = favoriteCatDao.getFavoriteCatById(catId)
            if (existingCat == null) {
                favoriteCatDao.insertFavoriteCat(FavoriteCat(catId))
            }
        }

    }

    suspend fun removeCatFromFavorites(catId: String) {
        withContext(Dispatchers.IO){
            val existingCat = favoriteCatDao.getFavoriteCatById(catId)
            if (existingCat != null) {
                favoriteCatDao.deleteFavoriteCat(existingCat)
            }
        }
    }

    fun getFavoriteCats(): Flow<List<String>> = flow {
        log("emitting on background thread")
        emit(favoriteCatDao.getFavoriteCatIds())
    }.flowOn(Dispatchers.IO)

}