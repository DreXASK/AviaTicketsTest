package com.drexask.aviatickets.di

import com.drexask.aviatickets.data.repository.TicketOfferRepositoryImpl
import com.drexask.aviatickets.data.repository.MusicFlightsRepositoryImpl
import com.drexask.aviatickets.data.repository.TicketsRepositoryImpl
import com.drexask.aviatickets.domain.repository.TicketOfferRepository
import com.drexask.aviatickets.domain.repository.MusicFlightsRepository
import com.drexask.aviatickets.domain.repository.TicketsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMusicFlightsRepository(
        musicFlightsRepositoryImpl: MusicFlightsRepositoryImpl
    ): MusicFlightsRepository

    @Binds
    @Singleton
    abstract fun provideTicketOffersRepository(
        ticketOfferRepositoryImpl: TicketOfferRepositoryImpl
    ): TicketOfferRepository

    @Binds
    @Singleton
    abstract fun provideTicketsRepository(
        ticketsRepositoryImpl: TicketsRepositoryImpl
    ): TicketsRepository
}