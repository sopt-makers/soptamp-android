package org.sopt.stamp.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MissionsResponse(
    val missions: List<MissionResponse>
) {
    @Serializable
    data class MissionResponse(
        val id: Int,
        val title: String,
        val level: Int,
        val profileImage: List<String>,
        val isCompleted: Boolean
    )
}
