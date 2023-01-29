package org.sopt.stamp.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.sopt.stamp.data.remote.api.StampService
import org.sopt.stamp.domain.model.Archive
import org.sopt.stamp.domain.repository.StampRepository
import org.sopt.stamp.feature.mission.model.ImageModel
import org.sopt.stamp.util.ContentUriRequestBody
import javax.inject.Inject

class StampRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: StampService,
    private val json: Json
) : StampRepository {
    @Serializable
    private data class Content(
        val content: String
    )

    override suspend fun completeMission(
        missionId: Int,
        imageUri: ImageModel,
        content: String
    ): Result<Unit> {
        val contentJson = json.encodeToString(Content(content))
        val contentRequestBody = contentJson.toRequestBody("application/json".toMediaType())
        val imageRequestBody = when (imageUri) {
            is ImageModel.Empty -> null
            is ImageModel.Local -> {
                imageUri.uri.map {
                    ContentUriRequestBody(context, it)
                }.map {
                    it.toFormData()
                }
            }

            is ImageModel.Remote -> null
        }
        return runCatching {
            service.registerStamp(
                missionId,
                contentRequestBody,
                imageRequestBody
            )
        }
    }

    override suspend fun getMissionContent(missionId: Int): Result<Archive> {
        return runCatching {
            service.retrieveStamp(missionId).toDomain()
        }
    }

    override suspend fun modifyMission(
        missionId: Int,
        imageUri: ImageModel,
        content: String
    ): Result<Unit> {
        val contentJson = json.encodeToString(Content(content))
        val contentRequestBody = contentJson.toRequestBody("application/json".toMediaType())
        val imageRequestBody = when (imageUri) {
            is ImageModel.Empty -> null
            is ImageModel.Local -> {
                imageUri.uri.map {
                    ContentUriRequestBody(context, it)
                }.map {
                    it.toFormData()
                }
            }

            is ImageModel.Remote -> null
        }
        return runCatching {
            service.modifyStamp(
                missionId,
                contentRequestBody,
                imageRequestBody
            )
        }
    }
}