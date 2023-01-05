package org.sopt.stamp.data.remote.mapper // ktlint-disable filename

import org.sopt.stamp.data.remote.model.MissionsData
import org.sopt.stamp.data.remote.model.response.MissionsResponse
import org.sopt.stamp.domain.model.Mission

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

internal fun List<MissionsData.MissionData>.toDomain(): List<Mission> = this.map { it.toDomain() }

internal fun MissionsData.MissionData.toDomain(): Mission = Mission(
    id = this.id,
    title = this.title,
    level = this.level,
    profileImage = this.profileImage,
    isCompleted = this.isCompleted
)
