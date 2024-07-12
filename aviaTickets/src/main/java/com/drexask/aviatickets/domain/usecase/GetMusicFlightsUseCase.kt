package com.drexask.aviatickets.domain.usecase

import com.drexask.aviatickets.domain.models.MusicFlight
import com.drexask.aviatickets.domain.repository.MusicFlightsRepository

class GetMusicFlightsUseCase(private val repository: MusicFlightsRepository) {

    suspend fun execute(): Result<List<MusicFlight>> {
        return repository.getMusicFlights()
    }
}