package org.benedetto.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.benedetto.data.model.FavoriteCat

@Database(entities = [FavoriteCat::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): FavoriteCatDao
}
