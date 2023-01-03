package org.sopt.stamp.remote.model

data class MissionsResponse(
    val missions: List<MissionResponse>
) {
    @kotlinx.serialization.Serializable
    data class MissionResponse(
        val id: Int,
        val title: String,
        val level: Int,
        val profileImage: List<String>,
        val isCompleted: Boolean
    )
}
