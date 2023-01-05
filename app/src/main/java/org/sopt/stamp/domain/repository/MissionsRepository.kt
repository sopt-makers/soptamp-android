package org.sopt.stamp.domain.repository

import org.sopt.stamp.domain.model.Missions

interface MissionsRepository {
    suspend fun getAllMissions(userId: Int): Result<Missions>
    suspend fun getCompleteMissions(userId: Int): Result<Missions>
    suspend fun getInCompleteMissions(userId: Int): Result<Missions>
}
