package org.benedetto.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class FavoriteCats(val id: Int) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface FavoriteCatsDao{
    @Query("SELECT * FROM favoritecats ORDER BY uid DESC")
    fun getFavoriteCats(): Flow<List<FavoriteCats>>

    @Insert
    suspend fun insertFavoriteCat(item: FavoriteCats)
}

/*
package com.example.template.core.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class DataItemType(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface DataItemTypeDao {
    @Query("SELECT * FROM dataitemtype ORDER BY uid DESC LIMIT 10")
    fun getDataItemTypes(): Flow<List<DataItemType>>

    @Insert
    suspend fun insertDataItemType(item: DataItemType)
}

 */