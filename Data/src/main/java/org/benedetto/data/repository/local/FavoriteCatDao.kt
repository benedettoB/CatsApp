package org.benedetto.data.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.benedetto.data.model.FavoriteCat

@Dao
interface FavoriteCatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCat(favoriteCat: FavoriteCat)

    @Query("SELECT * FROM favorite_cats WHERE catId = :catId")
    suspend fun getFavoriteCatById(catId: String): FavoriteCat?

    @Query("SELECT catId FROM favorite_cats")
    suspend fun getFavoriteCatIds(): List<String>

    @Delete
    suspend fun deleteFavoriteCat(favoriteCat: FavoriteCat)
}
