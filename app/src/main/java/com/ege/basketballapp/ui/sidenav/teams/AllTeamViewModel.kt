package com.ege.basketballapp.ui.sidenav.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ege.basketballapp.data.repository.DataRepository
import com.ege.basketballapp.model.Resource
import com.ege.basketballapp.model.Status
import com.ege.basketballapp.model.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AllTeamViewModel @Inject constructor(
    private val dataRepository: DataRepository,
) : ViewModel() {

    private var teamJob: Job? = null
    private val _teams =
        MutableStateFlow(Resource<TeamResponse>(Status.LOADING, null, null))
    val teams: StateFlow<Resource<TeamResponse>> = _teams.asStateFlow()


    init {
        getPlayers()
    }

    private fun getPlayers() {
        teamJob?.cancel()
        teamJob = viewModelScope.launch {
            dataRepository.getTeams().collect {
                if (it != null)
                    _teams.value = it

            }
        }
    }}