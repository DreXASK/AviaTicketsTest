package com.drexask.aviatickets.data.repository

import com.drexask.aviatickets.data.services.MusicFlightsService
import com.drexask.aviatickets.domain.models.MusicFlight
import com.drexask.aviatickets.domain.repository.MusicFlightsRepository
import javax.inject.Inject


class MusicFlightsRepositoryImpl @Inject constructor(
    private val musicFlightsService: MusicFlightsService
): MusicFlightsRepository {

    override suspend fun getMusicFlights(): Result<List<MusicFlight>> {
        try {
            val dataDto = musicFlightsService
                .getMusicFlights("1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav", "download")

            val data = dataDto.offers.map {
                it.mapToDomainModel()
            }

            return Result.success(data)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}