package com.drexask.aviatickets.domain.repository

import com.drexask.aviatickets.domain.models.MusicFlight

interface MusicFlightsRepository {

    suspend fun getMusicFlights(): Result<List<MusicFlight>>
}