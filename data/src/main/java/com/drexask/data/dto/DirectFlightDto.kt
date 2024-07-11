package com.drexask.data.dto

import com.drexask.domain.model.DirectFlight
import com.drexask.domain.model.MusicFlight
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectFlightDownloadedData(
    @SerialName("tickets_offers")
    val offers: List<DirectFlightDto>
)

@Serializable
data class DirectFlightDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("time_range")
    val timeRange: List<String>,
    @SerialName("price")
    val priceDto: PriceDto
) {
    fun mapToDomainModel(): DirectFlight {
        return DirectFlight(
            title = this.title,
            timeRange = this.timeRange,
            price = priceDto.mapToDomainModel()
        )
    }
}
