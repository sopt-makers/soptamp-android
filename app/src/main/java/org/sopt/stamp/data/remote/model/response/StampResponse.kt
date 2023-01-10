package org.sopt.stamp.data.remote.model.response

import kotlinx.serialization.Serializable
import org.sopt.stamp.domain.model.Archive

@Serializable
data class StampResponse(
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val id: Int,
    val contents: String,
    val images: List<String>,
    val userId: Int,
    val missionId: Int,
) {
    fun toDomain() = Archive(
        createdAt = createdAt,
        updatedAt = updatedAt,
        id = id,
        contents = contents,
        images = images,
        userId = userId,
        missionId = missionId,
    )
}
