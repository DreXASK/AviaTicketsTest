package com.drexask.aviatickets.domain.repository

import com.drexask.aviatickets.domain.models.Ticket

interface TicketsRepository {

    suspend fun getTickets(): Result<List<Ticket>>
}