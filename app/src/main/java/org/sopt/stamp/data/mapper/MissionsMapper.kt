package org.sopt.stamp.data.mapper // ktlint-disable filename

import org.sopt.stamp.data.remote.model.MissionData
import org.sopt.stamp.domain.model.Mission

internal fun List<MissionData>.toDomain(): List<Mission> = this.map { it.toDomain() }

internal fun MissionData.toDomain(): Mission = Mission(
    id = this.id,
    title = this.title,
    level = this.level,
    profileImage = this.profileImage,
    isCompleted = this.isCompleted
)