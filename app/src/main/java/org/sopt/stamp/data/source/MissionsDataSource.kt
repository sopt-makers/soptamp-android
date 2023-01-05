package org.sopt.stamp.data.source

import org.sopt.stamp.data.remote.model.MissionsData

interface MissionsDataSource {
    suspend fun getAllMission(userId: Int): Result<MissionsData>
    suspend fun getCompleteMission(userId: Int): Result<MissionsData>
    suspend fun getIncompleteMissions(userId: Int): Result<MissionsData>
}
