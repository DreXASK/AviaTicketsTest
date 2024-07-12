package com.drexask.aviatickets.presentation.models

import com.drexask.aviatickets.domain.models.PopularDestination

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
