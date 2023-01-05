package org.sopt.stamp.feature.mission

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.stamp.domain.model.MissionsFilter
import org.sopt.stamp.domain.repository.MissionsRepository
import org.sopt.stamp.feature.mission.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class MissionsViewModel @Inject constructor(
    private val missionsRepository: MissionsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MissionsState> = MutableStateFlow(MissionsState.Loading)
    val state: StateFlow<MissionsState> = _state.asStateFlow()

    init {
        fetchMissions()
    }

    fun fetchMissions() {
        _state.value = MissionsState.Loading
        fetchMissions(MissionsFilter.ALL_MISSION)
    }

    fun fetchMissions(filter: String) {
        _state.value = MissionsState.Loading
        fetchMissions(MissionsFilter.findFilterOf(filter))
    }

    private fun fetchMissions(filter: MissionsFilter) = viewModelScope.launch {
        val userId = 1
        val missions = when (filter) {
            MissionsFilter.ALL_MISSION -> missionsRepository.getAllMissions(userId)
            MissionsFilter.COMPLETE_MISSION -> missionsRepository.getCompleteMissions(userId)
            MissionsFilter.INCOMPLETE_MISSION -> missionsRepository.getInCompleteMissions(userId)
        }
        missions.mapCatching { it.toUiModel(filter.title) }
            .onSuccess { missions -> _state.value = MissionsState.Success(missions) }
            .onFailure { throwable ->
                Log.d("test", "$throwable")
                _state.value = MissionsState.Failure
            }
    }
}
