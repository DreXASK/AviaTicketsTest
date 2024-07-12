package com.drexask.domain.repository

import com.drexask.domain.models.DirectFlight

interface DirectFlightsRepository {

    suspend fun getDirectFlights(): Result<List<DirectFlight>>
}