package com.drexask.domain.models

data class DirectFlight(
    val title: String,
    val timeRange: List<String>,
    val price: Price
)
