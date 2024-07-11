package com.drexask.data

import com.drexask.data.dto.DirectFlightDownloadedData
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectFlightService {

    @GET("u/0/uc")
    suspend fun getDirectFlights(
        @Query("id") id: String,
        @Query("export") export: String
    ): DirectFlightDownloadedData
}