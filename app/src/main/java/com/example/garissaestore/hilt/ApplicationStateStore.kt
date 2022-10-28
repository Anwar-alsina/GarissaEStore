package com.example.garissaestore.hilt

import com.example.garissaestore.redux.ApplicationState
import com.example.garissaestore.redux.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStateStore {

    @Provides
    @Singleton
    fun providesApplicationStateStore(): Store<ApplicationState>{
        return Store(ApplicationState())
    }
}