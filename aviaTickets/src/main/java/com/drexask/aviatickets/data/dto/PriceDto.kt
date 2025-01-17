package com.drexask.aviatickets.data.dto

import com.drexask.aviatickets.domain.models.Price
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceDto(
    @SerialName("value")
    val value: Int
) {
    fun mapToDomainModel(): Price {
        return Price(
            value = this.value
        )
    }
}