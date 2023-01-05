package org.sopt.stamp.data.remote.api

import org.sopt.stamp.data.remote.model.response.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Header

internal interface SoptampService {
    @GET("mission/all")
    suspend fun getAllMissions(@Header("userId") userId: Int): List<MissionResponse>

    @GET("mission/complete")
    suspend fun getCompleteMissions(@Header("userId") userId: Int): List<MissionResponse>

    @GET("mission/incomplete")
    suspend fun getIncompleteMissions(@Header("userId") userId: Int): List<MissionResponse>
}
