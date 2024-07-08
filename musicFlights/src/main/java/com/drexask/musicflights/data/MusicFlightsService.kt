package com.drexask.musicflights.data

import com.drexask.musicflights.domain.model.DownloadedData
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicFlightsService {

    @GET("u/0/uc")
    suspend fun getMusicFlights(
        @Query("id") id: String,
        @Query("export") export: String
    ): DownloadedData
}