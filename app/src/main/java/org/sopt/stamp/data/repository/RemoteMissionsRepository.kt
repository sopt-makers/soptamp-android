package org.sopt.stamp.data.repository

import javax.inject.Inject
import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.data.mapper.toDomain
import org.sopt.stamp.data.source.MissionsDataSource
import org.sopt.stamp.domain.model.Mission
import org.sopt.stamp.domain.repository.MissionsRepository

internal class RemoteMissionsRepository @Inject constructor(
    private val remote: MissionsDataSource
) : MissionsRepository {
    override suspend fun getAllMissions(userId: Int): Result<List<Mission>> {
        val result = remote.getAllMission(userId)
            .mapCatching { it.toDomain() }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }

    override suspend fun getCompleteMissions(userId: Int): Result<List<Mission>> {
        val result = remote.getAllMission(userId)
            .mapCatching {
                it.filter { mission -> mission.isCompleted }.toDomain()
            }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }

    override suspend fun getInCompleteMissions(userId: Int): Result<List<Mission>> {
        val result = remote.getAllMission(userId)
            .mapCatching {
                it.filter { mission -> !mission.isCompleted }.toDomain()
            }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }
}
