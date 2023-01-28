package org.sopt.stamp.feature.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.stamp.data.local.SoptampDataStore
import org.sopt.stamp.domain.error.Error
import org.sopt.stamp.domain.model.MissionsFilter
import org.sopt.stamp.domain.repository.MissionsRepository
import org.sopt.stamp.feature.mission.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class MissionsViewModel @Inject constructor(
    private val missionsRepository: MissionsRepository,
    private val dataStore: SoptampDataStore
) : ViewModel() {

    private val _state: MutableStateFlow<MissionsState> = MutableStateFlow(MissionsState.Loading)
    val state: StateFlow<MissionsState> = _state.asStateFlow()

    init {
        fetchMissions()
    }

    fun fetchMissions(
        userId: Int? = null,
        filter: String? = null
    ) = viewModelScope.launch {
        _state.value = MissionsState.Loading
        dataStore.userId.collect { signedUserId ->
            fetchMissions(
                userId = userId ?: signedUserId,
                filter = filter?.let { MissionsFilter.findFilterOf(filter) } ?: MissionsFilter.ALL_MISSION
            )
        }
    }

    private suspend fun fetchMissions(userId: Int, filter: MissionsFilter) {
        val missions = when (filter) {
            MissionsFilter.ALL_MISSION -> missionsRepository.getAllMissions(userId)
            MissionsFilter.COMPLETE_MISSION -> missionsRepository.getCompleteMissions(userId)
            MissionsFilter.INCOMPLETE_MISSION -> missionsRepository.getInCompleteMissions(userId)
        }
        missions.mapCatching { it.toUiModel(filter.title) }
            .onSuccess { missions ->
                _state.value = MissionsState.Success(missions)
            }
            .onFailure { throwable ->
                when (throwable) {
                    is Error.NetworkUnavailable -> {
                        _state.value = MissionsState.Failure(throwable)
                    }

                    else -> throw throwable
                }
            }
    }
}