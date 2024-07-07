package com.drexask.musicflights.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MusicFlightDto(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
)

@Serializable
data class Price(
    val value: Int
)