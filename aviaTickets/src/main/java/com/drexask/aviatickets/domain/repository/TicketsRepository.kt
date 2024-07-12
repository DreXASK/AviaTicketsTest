package com.drexask.domain.repository

import com.drexask.domain.models.Ticket

interface TicketsRepository {

    suspend fun getTickets(): Result<List<Ticket>>
}