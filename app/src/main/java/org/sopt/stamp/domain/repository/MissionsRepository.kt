package org.sopt.stamp.domain.repository

import org.sopt.stamp.domain.model.Mission

interface MissionsRepository {
    suspend fun getAllMissions(userId: Int): Result<List<Mission>>
    suspend fun getCompleteMissions(userId: Int): Result<List<Mission>>
    suspend fun getInCompleteMissions(userId: Int): Result<List<Mission>>
}
