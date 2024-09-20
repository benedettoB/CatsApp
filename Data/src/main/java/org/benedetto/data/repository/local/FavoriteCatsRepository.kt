package org.benedetto.data.repository.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.benedetto.data.model.FavoriteCat
import javax.inject.Inject

class FavoriteCatsRepository @Inject constructor(private val favoriteCatDao: FavoriteCatDao) {

    /*
    Avoid blocking main thread
        By using withContext(Dispatchers.IO), you ensure that the network call and any other blocking operations are performed on a
        background thread, not blocking the main thread.

     */
    suspend fun addCatToFavorites(catId: String) {
        withContext(Dispatchers.IO) {
            val existingCat = favoriteCatDao.getFavoriteCatById(catId)
            if (existingCat == null) {
                favoriteCatDao.insertFavoriteCat(FavoriteCat(catId))
            }
        }
    }

    suspend fun removeCatFromFavorites(catId: String) {
        val existingCat = favoriteCatDao.getFavoriteCatById(catId)
        if (existingCat != null) {
            favoriteCatDao.deleteFavoriteCat(existingCat)
        }
    }


    suspend fun isCatFavorite(catId: String): Boolean {
        return withContext(Dispatchers.IO) {
            favoriteCatDao.getFavoriteCatById(catId) != null
        }
    }

    suspend fun getFavoriteCatIds(): List<String> {
        return withContext(Dispatchers.IO) {
            favoriteCatDao.getFavoriteCatIds()
        }
    }
}