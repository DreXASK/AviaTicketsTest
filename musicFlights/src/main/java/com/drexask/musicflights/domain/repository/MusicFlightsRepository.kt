package com.drexask.musicflights.domain.repository

import com.drexask.musicflights.domain.model.MusicFlightDto

interface MusicFlightsRepository {

    suspend fun getMusicFlights(): Result<List<MusicFlightDto>>
}