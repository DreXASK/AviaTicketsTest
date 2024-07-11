package com.drexask.domain.model

import java.time.LocalDateTime

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
    val dateTime: LocalDateTime,
    val airport: String
)

data class Arrival(
    val town: String,
    val dateTime: LocalDateTime,
    val airport: String
)
