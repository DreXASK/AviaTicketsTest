package com.drexask.domain.repository

import com.drexask.domain.models.MusicFlight

interface MusicFlightsRepository {

    suspend fun getMusicFlights(): Result<List<MusicFlight>>
}