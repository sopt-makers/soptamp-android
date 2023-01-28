package org.sopt.stamp.data.remote.source

import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.data.remote.api.SoptampService
import org.sopt.stamp.data.remote.mapper.toData
import org.sopt.stamp.data.remote.model.MissionData
import org.sopt.stamp.data.source.MissionsDataSource
import java.net.UnknownHostException
import javax.inject.Inject

internal class RemoteMissionsDataSource @Inject constructor(
    private val soptampService: SoptampService
) : MissionsDataSource {
    override suspend fun getAllMission(userId: Int): Result<List<MissionData>> {
        val result = kotlin.runCatching { soptampService.getAllMissions(userId).toData() }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}