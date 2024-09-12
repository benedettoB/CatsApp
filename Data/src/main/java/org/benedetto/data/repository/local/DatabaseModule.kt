package org.benedetto.data.repository.local

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CatDatabase {
        return Room.databaseBuilder(app, CatDatabase::class.java, "cat_database").build()
    }

    @Provides
    fun provideCatDao(db: CatDatabase): FavoriteCatDao {
        return db.catDao()
    }
}

