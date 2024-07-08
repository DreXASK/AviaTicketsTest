package com.drexask.musicflights.domain.usecase

import com.drexask.musicflights.domain.model.MusicFlightDto
import com.drexask.musicflights.domain.repository.MusicFlightsRepository
import javax.inject.Inject
import javax.inject.Singleton

class GetMusicFlightsUseCase @Inject constructor(private val repository: MusicFlightsRepository) {

    suspend fun execute(): Result<List<MusicFlightDto>> {
        return repository.getMusicFlights()
    }
}