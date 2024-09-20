package org.benedetto.data.repository.local

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* @Singleton
*   Scope: has Application-wide scope managed by Android Application class
*   Lifecycle: Lives as long as the application is alive
*   Usage: For dependencies that should exist across the entire lifespan of the app, such as repositories, database instances and
*          network clients
* */
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    internal fun provideDatabase(app: Application): CatDatabase {
        return Room.databaseBuilder(app, CatDatabase::class.java, "cat_database").build()
    }

    @Provides
    internal fun provideCatDao(db: CatDatabase): FavoriteCatDao {
        return db.catDao()
    }
}

/*
Component 	                 Scope 	                            Created at 	                            Destroyed at
SingletonComponent 	        @Singleton 	                        Application#onCreate() 	                Application process is destroyed
ActivityRetainedComponent 	@ActivityRetainedScoped 	        Activity#onCreate()1                    Activity#onDestroy()1
ViewModelComponent 	        @ViewModelScoped 	                ViewModel created 	                    ViewModel destroyed
ActivityComponent 	        @ActivityScoped 	                Activity#onCreate() 	                Activity#onDestroy()
FragmentComponent 	        @FragmentScoped 	                Fragment#onAttach() 	                Fragment#onDestroy()
ViewComponent 	            @ViewScoped 	                    View#super() 	                        View destroyed
ViewWithFragmentComponent 	@ViewScoped 	                    View#super() 	                        View destroyed
ServiceComponent 	        @ServiceScoped 	                    Service#onCreate() 	                    Service#onDestroy()

Scoped vs unscoped bindings

    By default, all bindings in Dagger are “unscoped”. This means that each time the binding is requested,
    Dagger will create a new instance of the binding.
    However, Dagger also allows a binding to be “scoped” to a particular component (see the scope annotations in the table above).
    A scoped binding will only be created once per instance of the component it’s scoped to,
    and all requests for that binding will share the same instance.
 */