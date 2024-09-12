package org.benedetto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCats::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favoriteCatsDao(): FavoriteCatsDao
}


/*


@Database(entities = [DataItemType::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataItemTypeDao(): DataItemTypeDao
}

 */