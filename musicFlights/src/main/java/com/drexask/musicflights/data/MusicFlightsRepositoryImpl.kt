package com.drexask.musicflights.data

import com.drexask.musicflights.domain.model.MusicFlightDto
import com.drexask.musicflights.domain.repository.MusicFlightsRepository
import java.io.IOException
import javax.inject.Inject


class MusicFlightsRepositoryImpl @Inject constructor(
    private val musicFlightsService: MusicFlightsService
) : MusicFlightsRepository {

    override suspend fun getMusicFlights(): Result<List<MusicFlightDto>> {
        try {
            val data = musicFlightsService.getMusicFlights("1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav", "download")
            return Result.success(data.offers)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}