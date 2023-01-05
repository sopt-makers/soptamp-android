package org.sopt.stamp.data.source

import org.sopt.stamp.data.model.MissionData

interface MissionsDataSource {
    suspend fun getAllMission(userId: Int): Result<List<MissionData>>
    suspend fun getCompleteMission(userId: Int): Result<List<MissionData>>
    suspend fun getIncompleteMissions(userId: Int): Result<List<MissionData>>
}