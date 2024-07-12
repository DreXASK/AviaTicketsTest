package com.drexask.aviatickets.data.services

import com.drexask.aviatickets.data.dto.MusicFlightDownloadedData
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicFlightsService {

    @GET("u/0/uc")
    suspend fun getMusicFlights(
        @Query("id") id: String,
        @Query("export") export: String
    ): MusicFlightDownloadedData
}