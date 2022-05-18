package com.ege.basketballapp.data.api

import com.ege.basketballapp.model.PlayerResponse
import com.ege.basketballapp.model.ServerResponse
import com.ege.basketballapp.model.TeamResponse
import retrofit2.Response
import retrofit2.http.GET


interface DataApi {
    @GET("5G4U")
    suspend fun players(
    ): Response<ServerResponse<PlayerResponse>>

    @GET("FKQ3")
    suspend fun teams(
    ): Response<ServerResponse<TeamResponse>>
}