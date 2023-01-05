package org.sopt.stamp.remote.mapper // ktlint-disable filename

import org.sopt.stamp.data.remote.model.MissionsData
import org.sopt.stamp.data.remote.model.response.MissionsResponse

internal fun MissionsResponse.toData(): MissionsData = MissionsData(
    missions = this.missions.map { it.toData() }
)

internal fun MissionsResponse.MissionResponse.toData(): MissionsData.MissionData = MissionsData.MissionData(
    id = this.id,
    title = this.title,
    level = this.level,
    profileImage = this.profileImage,
    isCompleted = this.isCompleted
)
