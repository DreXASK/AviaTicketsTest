package com.drexask.aviatickets.data

import com.drexask.aviatickets.data.repository.DirectFlightRepositoryImpl
import com.drexask.aviatickets.data.repository.MusicFlightsRepositoryImpl
import com.drexask.aviatickets.data.repository.TicketsRepositoryImpl
import com.drexask.aviatickets.data.services.DirectFlightService
import com.drexask.aviatickets.data.services.MusicFlightsService
import com.drexask.aviatickets.data.services.TicketsService
import com.drexask.aviatickets.domain.repository.DirectFlightsRepository
import com.drexask.aviatickets.domain.repository.MusicFlightsRepository
import com.drexask.aviatickets.domain.repository.TicketsRepository
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

    @Provides
    @Singleton
    fun provideTicketsRepository(
        ticketsService: TicketsService
    ): TicketsRepository = TicketsRepositoryImpl(ticketsService)
}