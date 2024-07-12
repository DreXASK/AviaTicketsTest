package com.drexask.domain.usecase

import com.drexask.domain.models.MusicFlight
import com.drexask.domain.repository.MusicFlightsRepository

class GetMusicFlightsUseCase(private val repository: MusicFlightsRepository) {

    suspend fun execute(): Result<List<MusicFlight>> {
        return repository.getMusicFlights()
    }
}