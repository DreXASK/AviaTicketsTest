package com.drexask.data.dto

import com.drexask.domain.model.MusicFlight
import com.drexask.domain.model.Price
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloadedData(
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

@Serializable
data class PriceDto(
    @SerialName("value")
    val value: Int
) {
    fun mapToDomainModel(): Price {
        return Price(
            value = this.value
        )
    }
}