package org.sopt.stamp.remote.api

import org.sopt.stamp.remote.model.MissionsResponse
import retrofit2.http.GET
import retrofit2.http.Header

internal interface SoptampService {
    @GET("/mission/all")
    suspend fun getAllMissions(@Header("userId") userId: Int): MissionsResponse

    @GET("/mission/complete")
    suspend fun getCompleteMissions(@Header("userId") userId: Int): MissionsResponse

    @GET("/mission/incomplete")
    suspend fun getIncompleteMissions(@Header("userId") userId: Int): MissionsResponse
}
