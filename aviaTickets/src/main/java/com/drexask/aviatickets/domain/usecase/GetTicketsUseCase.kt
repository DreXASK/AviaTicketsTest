package com.drexask.aviatickets.domain.usecase

import com.drexask.aviatickets.domain.models.Ticket
import com.drexask.aviatickets.domain.repository.TicketsRepository

class GetTicketsUseCase(private val repository: TicketsRepository) {

    suspend fun execute(): Result<List<Ticket>> {
        return repository.getTickets()
    }
}