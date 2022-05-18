package com.ege.basketballapp.data.dataSource

import com.ege.basketballapp.data.api.DataApi
import com.ege.basketballapp.model.PlayerResponse
import com.ege.basketballapp.model.Resource
import com.ege.basketballapp.model.TeamResponse
import com.ege.basketballapp.utils.GlobalPreferences
import com.ege.hayah.getResponse
import javax.inject.Inject

class DataRemoteDataSource @Inject constructor(
    private val dataApi: DataApi,
    private val globalPreferences: GlobalPreferences,
) {

    suspend fun getPlayers(): Resource<PlayerResponse> {
        return getResponse(
            request = { dataApi.players() },
            defaultErrorMessage = "Error fetching data"
        )
    }
    suspend fun getTeams(): Resource<TeamResponse> {
        return getResponse(
            request = { dataApi.teams() },
            defaultErrorMessage = "Error fetching data"
        )
    }
}