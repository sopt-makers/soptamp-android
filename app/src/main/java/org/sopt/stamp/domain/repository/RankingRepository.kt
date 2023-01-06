package org.sopt.stamp.domain.repository

import org.sopt.stamp.domain.model.Rank

interface RankingRepository {
    suspend fun getRanking(): Result<List<Rank>>
}
