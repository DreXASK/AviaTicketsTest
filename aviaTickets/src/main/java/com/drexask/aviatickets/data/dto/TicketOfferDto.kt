package com.drexask.aviatickets.data.dto

import com.drexask.aviatickets.domain.models.TicketOffer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketOfferDownloadedData(
    @SerialName("tickets_offers")
    val offers: List<TicketOfferDto>
)

@Serializable
data class TicketOfferDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("time_range")
    val timeRange: List<String>,
    @SerialName("price")
    val priceDto: PriceDto
) {
    fun mapToDomainModel(): TicketOffer {
        return TicketOffer(
            title = this.title,
            timeRange = this.timeRange,
            price = priceDto.mapToDomainModel()
        )
    }
}
