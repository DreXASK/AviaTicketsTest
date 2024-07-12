package com.drexask.aviatickets.di

import com.drexask.aviatickets.domain.repository.TicketOfferRepository
import com.drexask.aviatickets.domain.repository.MusicFlightsRepository
import com.drexask.aviatickets.domain.repository.TicketsRepository
import com.drexask.aviatickets.domain.usecase.GetTicketOffersUseCase
import com.drexask.aviatickets.domain.usecase.GetMusicFlightsUseCase
import com.drexask.aviatickets.domain.usecase.GetTicketsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTicketOffersUseCase(
        repository: TicketOfferRepository
    ): GetTicketOffersUseCase = GetTicketOffersUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMusicFlightsUseCase(
        repository: MusicFlightsRepository
    ): GetMusicFlightsUseCase = GetMusicFlightsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTicketsUseCase(
        repository: TicketsRepository
    ): GetTicketsUseCase = GetTicketsUseCase(repository)
}