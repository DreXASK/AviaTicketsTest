package com.drexask.aviatickets.domain.models

data class Ticket(
    val id: Int,
    val badge: String?,
    val price: Price,
    val departure: Departure,
    val arrival: Arrival,
    val hasTransfer: Boolean
)

data class Departure(
    val town: String,
    val localDateTime: String,
    val airport: String
)

data class Arrival(
    val town: String,
    val localDateTime: String,
    val airport: String
)
