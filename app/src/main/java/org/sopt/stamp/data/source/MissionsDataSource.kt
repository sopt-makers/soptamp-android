package org.sopt.stamp.data.source

import org.sopt.stamp.data.remote.model.MissionData

interface MissionsDataSource {
    suspend fun getAllMission(userId: Int): Result<List<MissionData>>
}
