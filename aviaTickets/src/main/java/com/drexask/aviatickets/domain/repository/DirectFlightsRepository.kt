package com.drexask.aviatickets.domain.repository

import com.drexask.aviatickets.domain.models.DirectFlight

interface DirectFlightsRepository {

    suspend fun getDirectFlights(): Result<List<DirectFlight>>
}