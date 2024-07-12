package com.drexask.aviatickets.data.repository

import com.drexask.aviatickets.data.services.TicketOfferService
import com.drexask.aviatickets.domain.models.TicketOffer
import com.drexask.aviatickets.domain.repository.TicketOfferRepository
import javax.inject.Inject

class TicketOfferRepositoryImpl @Inject constructor(
    private val ticketOfferService: TicketOfferService
) : TicketOfferRepository {

    override suspend fun getTicketOffers(): Result<List<TicketOffer>> {
        try {
            val dataDto = ticketOfferService.getTicketOffers(
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