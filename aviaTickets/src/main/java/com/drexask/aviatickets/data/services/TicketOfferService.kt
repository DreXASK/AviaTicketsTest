package com.drexask.aviatickets.data.services

import com.drexask.aviatickets.data.dto.TicketOfferDownloadedData
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketOfferService {

    @GET("u/0/uc")
    suspend fun getTicketOffers(
        @Query("id") id: String,
        @Query("export") export: String
    ): TicketOfferDownloadedData
}