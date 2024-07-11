package com.drexask.data.dto

import com.drexask.data.utils.LocalDateTimeSerializer
import com.drexask.domain.model.Arrival
import com.drexask.domain.model.Departure
import com.drexask.domain.model.Ticket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TicketsDownloadedData(
    @SerialName("tickets")
    val tickets: List<TicketDto>
)

@Serializable
data class TicketDto(
    @SerialName("id")
    val id: Int,
    @SerialName("badge")
    val badge: String? = null,
    @SerialName("price")
    val priceDto: PriceDto,
    @SerialName("departure")
    val departure: DepartureDto,
    @SerialName("arrival")
    val arrival: ArrivalDto,
    @SerialName("has_transfer")
    val hasTransfer: Boolean
) {
    fun mapToDomainModel(): Ticket {
        return Ticket(
            id = this.id,
            badge = this.badge,
            price = this.priceDto.mapToDomainModel(),
            departure = this.departure.mapToDomainModel(),
            arrival = this.arrival.mapToDomainModel(),
            hasTransfer = this.hasTransfer
        )
    }
}

@Serializable
data class DepartureDto(
    @SerialName("town")
    val town: String,
    @SerialName("date")
    @Serializable(with = LocalDateTimeSerializer::class) val dateTime: LocalDateTime,
    @SerialName("airport")
    val airport: String
) {
    fun mapToDomainModel(): Departure {
        return Departure(
            town = this.town,
            dateTime = this.dateTime,
            airport = this.airport
        )
    }
}

@Serializable
data class ArrivalDto(
    @SerialName("town")
    val town: String,
    @SerialName("date")
    @Serializable(with = LocalDateTimeSerializer::class) val date: LocalDateTime,
    @SerialName("airport")
    val airport: String
) {
    fun mapToDomainModel(): Arrival {
        return Arrival(
            town = this.town,
            dateTime = this.date,
            airport = this.airport
        )
    }
}