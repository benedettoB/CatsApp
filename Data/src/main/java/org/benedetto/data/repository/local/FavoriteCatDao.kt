package org.benedetto.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.benedetto.data.model.FavoriteCat

@Dao
interface FavoriteCatDao {
    @Insert
    suspend fun insertFavoriteCat(favoriteCat: FavoriteCat)

    @Query("SELECT * FROM favorite_cats WHERE catId = :catId")
    suspend fun getFavoriteCatById(catId: String): FavoriteCat?

    @Query("SELECT catId FROM favorite_cats")
    fun getFavoriteCatIds(): List<String>
}

