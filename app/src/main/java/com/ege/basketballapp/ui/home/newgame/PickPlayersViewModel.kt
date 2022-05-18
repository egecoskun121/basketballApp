package com.ege.basketballapp.ui.home.newgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ege.basketballapp.data.repository.DataRepository
import com.ege.basketballapp.model.PlayerResponse
import com.ege.basketballapp.model.Resource
import com.ege.basketballapp.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PickPlayersViewModel @Inject constructor(
    private val dataRepository: DataRepository,
) : ViewModel() {

    private var playerJob: Job? = null
    private val _players =
        MutableStateFlow(Resource<PlayerResponse>(Status.LOADING, null, null))
    val players: StateFlow<Resource<PlayerResponse>> = _players.asStateFlow()


    init {
        getPlayers()
    }

    private fun getPlayers() {
        playerJob?.cancel()
        playerJob = viewModelScope.launch {
            dataRepository.getPlayers().collect {
                if (it != null)
                    _players.value = it

            }
        }
    }
}