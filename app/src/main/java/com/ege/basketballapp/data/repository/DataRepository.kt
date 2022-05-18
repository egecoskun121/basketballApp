package com.ege.basketballapp.data.repository

import com.ege.basketballapp.data.dataSource.DataRemoteDataSource
import com.ege.basketballapp.model.PlayerResponse
import com.ege.basketballapp.model.Resource
import com.ege.basketballapp.model.Status
import com.ege.basketballapp.model.TeamResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataSource: DataRemoteDataSource,
) {

    private val playersMutex = Mutex()
    private var playersData: Resource<PlayerResponse>? = null
    private val teamsMutex = Mutex()
    private var teamsData: Resource<TeamResponse>? = null


    suspend fun getPlayers(): Flow<Resource<PlayerResponse>?> {
        return flow {
            emit(Resource.loading())
            emit(getPlayersCashed())
            val result = dataSource.getPlayers()
            if (result.status == Status.SUCCESS) {
                result.let {
                    playersMutex.withLock {
                        playersData = it
                    }
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getTeams(): Flow<Resource<TeamResponse>?> {
        return flow {
            emit(Resource.loading())
            emit(getTeamsCashed())
            val result = dataSource.getTeams()
            if (result.status == Status.SUCCESS) {
                result.let {
                    teamsMutex.withLock {
                        teamsData = it
                    }
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)

    }


    private suspend fun getPlayersCashed(): Resource<PlayerResponse>? {
        return playersMutex.withLock {
            this.playersData
        }
    }

    private suspend fun getTeamsCashed(): Resource<TeamResponse>? {
        return teamsMutex.withLock {
            this.teamsData
        }
    }
}