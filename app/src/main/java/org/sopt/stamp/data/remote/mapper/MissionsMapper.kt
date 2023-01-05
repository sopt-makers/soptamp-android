package org.sopt.stamp.data.remote.mapper // ktlint-disable filename

import org.sopt.stamp.data.remote.model.MissionData
import org.sopt.stamp.data.remote.model.response.MissionResponse

internal fun MissionResponse.toData(): MissionData = MissionData(
    id = this.id,
    title = this.title,
    level = this.level,
    profileImage = this.profileImage,
    isCompleted = this.isCompleted
)

internal fun List<MissionResponse>.toData(): List<MissionData> =
    this.map { it.toData() }
