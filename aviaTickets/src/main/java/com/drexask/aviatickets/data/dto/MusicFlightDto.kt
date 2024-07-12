package com.drexask.aviatickets.data.dto

import com.drexask.domain.models.MusicFlight
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicFlightDownloadedData(
    @SerialName("offers")
    val offers: List<MusicFlightDto>
)

@Serializable
data class MusicFlightDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("town")
    val town: String,
    @SerialName("price")
    val priceDto: PriceDto
) {
    fun mapToDomainModel(): MusicFlight {
        return MusicFlight(
            id = this.id,
            title = this.title,
            town = this.town,
            price = priceDto.mapToDomainModel()
        )
    }
}

