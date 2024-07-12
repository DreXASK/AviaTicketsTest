package com.drexask.presentation.models

import com.drexask.domain.models.DirectFlight

data class DirectFlightUi(
    val title: String,
    val timeRange: List<String>,
    val priceUi: PriceUi
) {
    companion object {
        fun mapFromDomainModel(directFlight: DirectFlight): DirectFlightUi {
            return DirectFlightUi(
                title = directFlight.title,
                timeRange = directFlight.timeRange,
                priceUi = PriceUi.mapFromDomainModel(directFlight.price)
            )
        }
    }
}
