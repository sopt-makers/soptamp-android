/*
 * Copyright 2023 SOPT - Shout Our Passion Together
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sopt.stamp.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.sopt.stamp.data.local.SoptampDataStore
import org.sopt.stamp.data.remote.api.StampService
import org.sopt.stamp.domain.model.Archive
import org.sopt.stamp.domain.repository.StampRepository
import org.sopt.stamp.feature.mission.model.ImageModel
import org.sopt.stamp.util.ContentUriRequestBody
import javax.inject.Inject

class StampRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: StampService,
    private val json: Json,
    private val dataStore: SoptampDataStore
) : StampRepository {
    @Serializable
    private data class Content(
        @SerialName("contents")
        val contents: String
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
                    it.toFormData("imgUrl")
                }
            }

            is ImageModel.Remote -> null
        }
        return runCatching {
            service.registerStamp(
                userId = dataStore.userId,
                missionId = missionId,
                stampContent = contentRequestBody,
                imgUrl = imageRequestBody
            )
        }
    }

    override suspend fun getMissionContent(missionId: Int): Result<Archive> {
        return runCatching {
            service.retrieveStamp(
                userId = dataStore.userId,
                missionId = missionId
            ).toDomain()
        }
    }

    override suspend fun getMissionContent(userId: Int, missionId: Int): Result<Archive> {
        return runCatching {
            service.retrieveStamp(
                userId = userId,
                missionId = missionId
            ).toDomain()
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
                    it.toFormData("imgUrl")
                }
            }

            is ImageModel.Remote -> listOf(ContentUriRequestBody(context, null).toFormData("imgUrl"))
        }
        return runCatching {
            service.modifyStamp(
                userId = dataStore.userId,
                missionId = missionId,
                stampContent = contentRequestBody,
                imgUrl = imageRequestBody
            )
        }
    }

    override suspend fun deleteMission(missionId: Int): Result<Unit> {
        return runCatching {
            service.deleteStamp(
                userId = dataStore.userId,
                missionId = missionId
            )
        }
    }

    override suspend fun deleteAllStamps(userId: Int): Result<Unit> {
        return runCatching { service.deleteAllStamps(userId) }
    }
}
