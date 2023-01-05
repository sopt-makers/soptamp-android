package org.sopt.stamp.data.model.response

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ResponseStamp(
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val id: Long,
    val contents: String,
    val images: List<String>,
    val userId: Long,
    val missionId: Int,
)
