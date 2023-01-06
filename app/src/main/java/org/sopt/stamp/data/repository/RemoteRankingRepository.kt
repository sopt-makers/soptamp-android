package org.sopt.stamp.data.repository

import javax.inject.Inject
import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.data.mapper.toDomain
import org.sopt.stamp.data.source.RankingDataSource
import org.sopt.stamp.domain.model.Rank
import org.sopt.stamp.domain.repository.RankingRepository

internal class RemoteRankingRepository @Inject constructor(
    private val remote: RankingDataSource
) : RankingRepository {
    override suspend fun getRanking(): Result<List<Rank>> {
        val result = remote.getRanking()
            .mapCatching { it.toDomain() }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }
}
