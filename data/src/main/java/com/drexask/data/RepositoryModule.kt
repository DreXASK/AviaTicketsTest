package com.drexask.data

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

}