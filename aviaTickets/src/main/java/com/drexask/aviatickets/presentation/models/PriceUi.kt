package com.drexask.presentation.models

import com.drexask.domain.models.DirectFlight
import com.drexask.domain.models.Price

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
