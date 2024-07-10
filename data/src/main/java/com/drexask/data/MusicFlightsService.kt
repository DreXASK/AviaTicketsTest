package com.drexask.data


import com.drexask.data.dto.DownloadedData
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicFlightsService {

    @GET("u/0/uc")
    suspend fun getMusicFlights(
        @Query("id") id: String,
        @Query("export") export: String
    ): DownloadedData
}