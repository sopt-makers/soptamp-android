package org.sopt.stamp.data.remote.api

import org.sopt.stamp.data.remote.model.response.RankResponse
import retrofit2.http.GET

internal interface RankService {

    @GET("rank")
    suspend fun getRanking(): List<RankResponse>

    @GET("rank/detail")
    suspend fun getRankDetail(): List<RankResponse>
}