package org.sopt.stamp.feature.mission.model

import org.sopt.stamp.domain.MissionLevel
import org.sopt.stamp.domain.model.Mission

data class MissionListUiModel(
    val title: String,
    val missionList: List<MissionUiModel>
)

// TODO: filter 부분은 나중에 삭제하기
fun List<Mission>.toUiModel(title: String): MissionListUiModel = MissionListUiModel(
    title = title,
    missionList = this
        .filter { it.level <= 3 }
        .map { it.toUiModel() }
)

fun Mission.toUiModel(): MissionUiModel = MissionUiModel(
    id = this.id,
    title = this.title,
    level = MissionLevel.of(this.level),
    isCompleted = this.isCompleted
)
