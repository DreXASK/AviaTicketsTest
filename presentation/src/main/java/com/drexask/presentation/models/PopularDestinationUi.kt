package com.drexask.presentation.models

import com.drexask.domain.models.DirectFlight
import com.drexask.domain.models.PopularDestination

data class PopularDestinationUi(
    val id: Int,
    val city: String,
    val info: String
) {
    companion object {
        fun mapFromDomainModel(popularDestination: PopularDestination): PopularDestinationUi {
            return PopularDestinationUi(
                id = popularDestination.id,
                city = popularDestination.city,
                info = popularDestination.info
            )
        }
    }
}
