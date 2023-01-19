package org.sopt.stamp.feature.mission

import org.sopt.stamp.domain.error.Error
import org.sopt.stamp.feature.mission.model.MissionListUiModel

sealed class MissionsState {
    object Loading : MissionsState()

    data class Success(val missionListUiModel: MissionListUiModel) : MissionsState()

    data class Failure(val error: Error) : MissionsState()
}
