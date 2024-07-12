package com.drexask.aviatickets.domain.models

data class MusicFlight(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
)