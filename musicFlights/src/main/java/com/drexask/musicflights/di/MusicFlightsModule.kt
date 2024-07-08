package com.drexask.musicflights.di

import com.drexask.musicflights.data.MusicFlightsRepositoryImpl
import com.drexask.musicflights.data.MusicFlightsService
import com.drexask.musicflights.domain.repository.MusicFlightsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicFlightsModule {

    @Provides
    @Singleton
    fun provideMusicFlightsService(
        @Named("drive.usercontent.google.com") retrofit: Retrofit
    ): MusicFlightsService = retrofit.create<MusicFlightsService>()

    @Provides
    @Singleton
    fun provideMusicFlightsRepository(
        musicFlightsService: MusicFlightsService
    ): MusicFlightsRepository = MusicFlightsRepositoryImpl(musicFlightsService)

}