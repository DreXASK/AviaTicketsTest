package com.drexask.data.services

import com.drexask.data.dto.TicketsDownloadedData
import retrofit2.http.GET
import retrofit2.http.Query


interface TicketsService {

    @GET("u/0/uc")
    suspend fun getTickets(
        @Query("id") id: String,
        @Query("export") export: String
    ): TicketsDownloadedData
}