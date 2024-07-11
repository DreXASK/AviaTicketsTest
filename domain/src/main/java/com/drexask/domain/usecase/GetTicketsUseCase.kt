package com.drexask.domain.usecase

import com.drexask.domain.model.Ticket
import com.drexask.domain.repository.TicketsRepository

class GetTicketsUseCase(private val repository: TicketsRepository) {

    suspend fun execute(): Result<List<Ticket>> {
        return repository.getTickets()
    }
}