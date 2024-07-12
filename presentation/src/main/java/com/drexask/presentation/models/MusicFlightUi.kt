package com.drexask.presentation.models

import com.drexask.domain.models.DirectFlight
import com.drexask.domain.models.MusicFlight

data class MusicFlightUi(
    val id: Int,
    val title: String,
    val town: String,
    val priceUi: PriceUi
) {
    companion object {
        fun mapFromDomainModel(musicFlight: MusicFlight): MusicFlightUi {
            return MusicFlightUi(
                id = musicFlight.id,
                title = musicFlight.title,
                town = musicFlight.town,
                priceUi = PriceUi.mapFromDomainModel(musicFlight.price)
            )
        }
    }
}