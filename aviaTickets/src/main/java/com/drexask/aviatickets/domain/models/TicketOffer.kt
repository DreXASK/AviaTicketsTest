package com.drexask.aviatickets.domain.models

data class TicketOffer(
    val title: String,
    val timeRange: List<String>,
    val price: Price
)
