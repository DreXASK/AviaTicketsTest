package com.drexask.data.repository

import com.drexask.data.services.TicketsService
import com.drexask.domain.model.Ticket
import com.drexask.domain.repository.TicketsRepository
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(
    private val ticketsService: TicketsService
): TicketsRepository {

    override suspend fun getTickets(): Result<List<Ticket>> {
        try {
            val dataDto = ticketsService
                .getTickets("1HogOsz4hWkRwco4kud3isZHFQLUAwNBA", "download")

            val data = dataDto.tickets.map {
                it.mapToDomainModel()
            }

            return Result.success(data)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}