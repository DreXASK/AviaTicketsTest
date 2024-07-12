package com.drexask.presentation.models

import com.drexask.domain.models.Arrival
import com.drexask.domain.models.Departure
import com.drexask.domain.models.Ticket
import java.time.LocalDateTime

data class TicketUi(
    val id: Int,
    val badge: String?,
    val priceUi: PriceUi,
    val departureUi: DepartureUi,
    val arrivalUi: ArrivalUi,
    val hasTransfer: Boolean
) {
    companion object {
        fun mapFromDomainModel(ticket: Ticket): TicketUi {
            return TicketUi(
                id = ticket.id,
                badge = ticket.badge,
                priceUi = PriceUi.mapFromDomainModel(ticket.price),
                departureUi = DepartureUi.mapFromDomainModel(ticket.departure),
                arrivalUi = ArrivalUi.mapFromDomainModel(ticket.arrival),
                hasTransfer = ticket.hasTransfer
            )
        }
    }
}

data class DepartureUi(
    val town: String,
    val localDateTime: LocalDateTime,
    val airport: String
) {
    companion object {
        fun mapFromDomainModel(departure: Departure): DepartureUi {
            return DepartureUi(
                town = departure.town,
                localDateTime = LocalDateTime.parse(departure.localDateTime),
                airport = departure.airport

            )
        }
    }
}

data class ArrivalUi(
    val town: String,
    val localDateTime: LocalDateTime,
    val airport: String
) {
    companion object {
        fun mapFromDomainModel(arrival: Arrival): ArrivalUi {
            return ArrivalUi(
                town = arrival.town,
                localDateTime = LocalDateTime.parse(arrival.localDateTime),
                airport = arrival.airport
            )
        }
    }
}
