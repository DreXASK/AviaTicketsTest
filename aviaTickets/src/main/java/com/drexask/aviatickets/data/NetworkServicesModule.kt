package com.drexask.aviatickets.data

import com.drexask.aviatickets.data.services.DirectFlightService
import com.drexask.aviatickets.data.services.MusicFlightsService
import com.drexask.aviatickets.data.services.TicketsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServicesModule {

    @Provides
    @Singleton
    fun provideMusicFlightsService(
        @Named("drive.usercontent.google.com") retrofit: Retrofit
    ): MusicFlightsService = retrofit.create()

    @Provides
    @Singleton
    fun provideDirectFlightsService(
        @Named("drive.usercontent.google.com") retrofit: Retrofit
    ): DirectFlightService = retrofit.create()

    @Provides
    @Singleton
    fun provideTicketsService(
        @Named("drive.google.com") retrofit: Retrofit
    ): TicketsService = retrofit.create()

}