package com.drexask.data

import com.drexask.data.repository.DirectFlightRepositoryImpl
import com.drexask.data.repository.MusicFlightsRepositoryImpl
import com.drexask.domain.repository.DirectFlightsRepository
import com.drexask.domain.repository.MusicFlightsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMusicFlightsRepository(
        musicFlightsService: MusicFlightsService
    ): MusicFlightsRepository = MusicFlightsRepositoryImpl(musicFlightsService)

    @Provides
    @Singleton
    fun provideDirectFlightsRepository(
        directFlightService: DirectFlightService
    ): DirectFlightsRepository = DirectFlightRepositoryImpl(directFlightService)

}