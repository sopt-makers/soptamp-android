package org.sopt.stamp.domain.repository

import org.sopt.stamp.domain.model.Archive
import org.sopt.stamp.feature.mission.model.ImageModel

interface StampRepository {
    suspend fun completeMission(
        missionId: Int,
        imageUri: ImageModel,
        content: String
    ): Result<Unit>

    suspend fun getMissionContent(
        missionId: Int
    ): Result<Archive>

    suspend fun modifyMission(
        missionId: Int,
        imageUri: ImageModel,
        content: String
    ): Result<Unit>
}