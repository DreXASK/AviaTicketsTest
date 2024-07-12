package com.drexask.aviatickets.domain.usecase

import com.drexask.aviatickets.domain.models.DirectFlight
import com.drexask.aviatickets.domain.repository.DirectFlightsRepository


class GetDirectFlightsUseCase(private val repository: DirectFlightsRepository) {

    suspend fun execute(): Result<List<DirectFlight>> {
        return repository.getDirectFlights()
    }
}