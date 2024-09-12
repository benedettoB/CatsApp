package org.benedetto.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cats")
data class FavoriteCat(
    @PrimaryKey val catId: String
)
