package com.drexask.aviatickets.presentation.models

import com.drexask.aviatickets.domain.models.MusicFlight

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