package org.sopt.stamp.data.remote.source

import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.data.remote.api.RankService
import org.sopt.stamp.data.remote.mapper.toData
import org.sopt.stamp.data.remote.model.RankData
import org.sopt.stamp.data.source.RankingDataSource
import java.net.UnknownHostException
import javax.inject.Inject

internal class RemoteRankingDataSource @Inject constructor(
    private val rankService: RankService
) : RankingDataSource {
    override suspend fun getRanking(): Result<List<RankData>> {
        val result = kotlin.runCatching {
            rankService.getRanking().toData()
        }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}