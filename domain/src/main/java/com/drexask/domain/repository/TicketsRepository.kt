package com.drexask.domain.repository

import com.drexask.domain.model.Ticket

interface TicketsRepository {

    suspend fun getTickets(): Result<List<Ticket>>
}