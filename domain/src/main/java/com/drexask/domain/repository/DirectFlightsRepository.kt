package com.drexask.domain.repository

import com.drexask.domain.model.DirectFlight

interface DirectFlightsRepository {

    suspend fun getDirectFlights(): Result<List<DirectFlight>>
}