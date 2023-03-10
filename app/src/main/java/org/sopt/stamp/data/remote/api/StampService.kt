package org.sopt.stamp.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.stamp.data.remote.model.response.ModifyStampResponse
import org.sopt.stamp.data.remote.model.response.StampResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface StampService {
    @GET("stamp/{missionId}")
    suspend fun retrieveStamp(
        @Header("userId") userId: Int = 158,
        @Path("missionId") missionId: Int
    ): StampResponse

    @Multipart
    @PUT("stamp/{missionId}")
    suspend fun modifyStamp(
        @Path("missionId") missionId: Int,
        @Part stampContent: RequestBody,
        @Part imageUrl: List<MultipartBody.Part>? = null
    ): ModifyStampResponse

    @Multipart
    @POST("stamp/{missionId}")
    suspend fun registerStamp(
        @Header("userId") userId: Int = 158,
        @Path("missionId") missionId: Int,
        @Part("stampContent") stampContent: RequestBody,
        @Part imgUrl: List<MultipartBody.Part>? = null
    ): StampResponse

    @DELETE("stamp/{missionId}")
    suspend fun deleteStamp(@Path("missionId") missionId: Int)
}