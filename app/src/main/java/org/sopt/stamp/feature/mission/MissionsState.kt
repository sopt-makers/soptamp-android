package org.sopt.stamp.feature.mission

import org.sopt.stamp.feature.mission.model.MissionListUiModel

sealed class MissionsState {
    object Loading : MissionsState()

    data class Success(val missionListUiModel: MissionListUiModel) : MissionsState()

    object Failure : MissionsState()
}
