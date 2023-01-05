package org.sopt.stamp.data.remote.service

import org.sopt.stamp.data.model.response.ResponseStampDto
import retrofit2.http.GET
import retrofit2.http.Path

interface StampService {
    @GET("stamp/{missionId}")
    suspend fun retrieveStamp(
        @Path("missionId") missionId: Int
    ): ResponseStampDto
}
