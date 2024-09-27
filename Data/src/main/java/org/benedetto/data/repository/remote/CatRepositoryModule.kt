package org.benedetto.data.repository.remote

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CatRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCatRepository(
        catRepositoryImpl: CatRepositoryImpl
    ): CatRepository
}
