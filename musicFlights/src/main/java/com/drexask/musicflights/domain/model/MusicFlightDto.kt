package com.drexask.musicflights.domain.model

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
    val price: Price
)

@Serializable
data class Price(
    @SerialName("value")
    val value: Int
)