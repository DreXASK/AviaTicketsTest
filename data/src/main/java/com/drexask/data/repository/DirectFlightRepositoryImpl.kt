package com.drexask.data.repository

import com.drexask.data.services.DirectFlightService
import com.drexask.domain.model.DirectFlight
import com.drexask.domain.repository.DirectFlightsRepository
import javax.inject.Inject

class DirectFlightRepositoryImpl @Inject constructor(
    private val directFlightService: DirectFlightService
) : DirectFlightsRepository {

    override suspend fun getDirectFlights(): Result<List<DirectFlight>> {
        try {
            val dataDto = directFlightService.getDirectFlights(
                "13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta",
                "download"
            )
            val data = dataDto.offers.map { it.mapToDomainModel() }
            return Result.success(data)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}