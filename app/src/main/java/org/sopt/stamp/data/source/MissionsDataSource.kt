package org.sopt.stamp.data.source

import org.sopt.stamp.data.remote.model.MissionsData

interface MissionsDataSource {
    suspend fun getAllMission(userId: Int): Result<List<MissionsData.MissionData>>
    suspend fun getCompleteMission(userId: Int): Result<List<MissionsData.MissionData>>
    suspend fun getIncompleteMissions(userId: Int): Result<List<MissionsData.MissionData>>
}
