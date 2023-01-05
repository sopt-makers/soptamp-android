package org.sopt.stamp.data.remote.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.stamp.data.model.response.ResponseModifyStampDto
import org.sopt.stamp.data.model.response.ResponseStampDto
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface StampService {
    @GET("stamp/{missionId}")
    suspend fun retrieveStamp(
        @Path("missionId") missionId: Int
    ): ResponseStampDto

    @Multipart
    @PUT("stamp/{missionId}")
    suspend fun modifyStamp(
        @Path("missionId") missionId: Int,
        @Part stampContent: RequestBody,
        @Part imageUrl: MultipartBody.Part? = null,
    ): ResponseModifyStampDto
}
