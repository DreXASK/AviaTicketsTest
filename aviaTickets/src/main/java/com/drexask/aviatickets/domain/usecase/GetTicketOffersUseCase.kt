package com.drexask.aviatickets.domain.usecase

import com.drexask.aviatickets.domain.models.TicketOffer
import com.drexask.aviatickets.domain.repository.TicketOfferRepository


class GetTicketOffersUseCase(private val repository: TicketOfferRepository) {

    suspend fun execute(): Result<List<TicketOffer>> {
        return repository.getTicketOffers()
    }
}