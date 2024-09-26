package org.benedetto.data.repository.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.benedetto.data.model.FavoriteCat
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
    suspend fun isCatFavorite(catId: String): Boolean {
        return favoriteCatDao.getFavoriteCatById(catId) != null

    }
   suspend fun getFavoriteCatIds(): List<String> {
        return favoriteCatDao.getFavoriteCatIds()
    }
}