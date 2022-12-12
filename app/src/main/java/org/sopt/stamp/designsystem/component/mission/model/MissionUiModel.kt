package org.sopt.stamp.designsystem.component.mission.model

import org.sopt.stamp.domain.MissionLevel

data class MissionUiModel(
    val id: Int,
    val title: String,
    val level: MissionLevel,
    val isCompleted: Boolean
)
