package com.drexask.aviatickets.presentation.models

import com.drexask.aviatickets.domain.models.Price

data class PriceUi(
    val value: Int
) {
    companion object {
        fun mapFromDomainModel(price: Price): PriceUi {
            return PriceUi(
                value = price.value
            )
        }
    }
}
