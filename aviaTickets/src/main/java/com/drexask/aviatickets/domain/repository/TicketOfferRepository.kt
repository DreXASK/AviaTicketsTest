package com.drexask.aviatickets.domain.repository

import com.drexask.aviatickets.domain.models.TicketOffer

interface TicketOfferRepository {

    suspend fun getTicketOffers(): Result<List<TicketOffer>>
}