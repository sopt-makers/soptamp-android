package org.sopt.stamp.remote.source

import java.net.UnknownHostException
import javax.inject.Inject
import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.data.model.MissionsData
import org.sopt.stamp.data.source.MissionsDataSource
import org.sopt.stamp.remote.api.SoptampService
import org.sopt.stamp.remote.mapper.toData

internal class RemoteMissionsDataSource @Inject constructor(
    private val soptampService: SoptampService
) : MissionsDataSource {
    override suspend fun getAllMission(userId: Int): Result<MissionsData> {
        val result = kotlin.runCatching { soptampService.getAllMissions(userId).toData() }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun getCompleteMission(userId: Int): Result<MissionsData> {
        val result = kotlin.runCatching { soptampService.getCompleteMissions(userId).toData() }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun getIncompleteMissions(userId: Int): Result<MissionsData> {
        val result = kotlin.runCatching { soptampService.getIncompleteMissions(userId).toData() }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}
