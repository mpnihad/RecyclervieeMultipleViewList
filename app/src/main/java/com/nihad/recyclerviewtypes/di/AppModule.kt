package com.nihad.recyclerviewtypes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.nihad.recyclerviewtypes.data.network.Api
import com.nihad.recyclerviewtypes.data.network.RemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTargetApi(
        remoteDataSource: RemoteDataSource
    ): Api {
        return remoteDataSource.buildApi(Api::class.java)
    }
}