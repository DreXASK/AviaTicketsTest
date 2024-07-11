package com.drexask.domain.usecase

import com.drexask.domain.model.DirectFlight
import com.drexask.domain.repository.DirectFlightsRepository


class GetDirectFlightsUseCase (private val repository: DirectFlightsRepository) {

    suspend fun execute(): Result<List<DirectFlight>> {
        return repository.getDirectFlights()
    }
}