package com.drexask.aviatickets.presentation.models

import com.drexask.aviatickets.domain.models.TicketOffer

data class TicketOfferUi(
    val title: String,
    val timeRange: List<String>,
    val priceUi: PriceUi
) {
    companion object {
        fun mapFromDomainModel(ticketOffer: TicketOffer): TicketOfferUi {
            return TicketOfferUi(
                title = ticketOffer.title,
                timeRange = ticketOffer.timeRange,
                priceUi = PriceUi.mapFromDomainModel(ticketOffer.price)
            )
        }
    }
}
