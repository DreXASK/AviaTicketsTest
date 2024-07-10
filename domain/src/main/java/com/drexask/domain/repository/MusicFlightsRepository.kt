package com.drexask.domain.repository

import com.drexask.domain.model.MusicFlight

interface MusicFlightsRepository {

    suspend fun getMusicFlights(): Result<List<MusicFlight>>
}