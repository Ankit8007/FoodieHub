package com.app.foodiehub.di

import com.app.foodiehub.appServices.repository.DataRepository
import com.app.foodiehub.appServices.repository.IDataRepository
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
 class DataModule {
//    @Binds
//    @Provides
//    @Singleton
    fun provideDataRepository(): IDataRepository{
        return DataRepository()
    }
}