package org.sopt.stamp.feature.mission.model

import org.sopt.stamp.designsystem.component.mission.model.MissionUiModel

data class MissionListUiModel(
    val title: String,
    val missionList: List<MissionUiModel>
)
