package org.sopt.stamp.data.source

import org.sopt.stamp.data.remote.model.RankData

interface RankingDataSource {
    suspend fun getRanking(): Result<List<RankData>>
}