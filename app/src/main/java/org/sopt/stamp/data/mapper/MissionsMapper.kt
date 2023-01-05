package org.sopt.stamp.data.mapper // ktlint-disable filename

import org.sopt.stamp.data.remote.model.MissionsData
import org.sopt.stamp.domain.model.Missions

internal fun MissionsData.toDomain(): Missions = Missions(
    missions = this.missions.map { it.toDomain() }
)
internal fun MissionsData.MissionData.toDomain(): Missions.Mission = Missions.Mission(
    id = this.id,
    title = this.title,
    level = this.level,
    profileImage = this.profileImage,
    isCompleted = this.isCompleted
)
