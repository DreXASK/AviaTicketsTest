package com.drexask.data

import com.drexask.data.services.DirectFlightService
import com.drexask.data.services.MusicFlightsService
import com.drexask.data.services.TicketsService
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