package org.sopt.stamp.data.remote.model

data class MissionsData(
    val missions: List<MissionData>
) {
    data class MissionData(
        val id: Int,
        val title: String,
        val level: Int,
        val profileImage: List<String>,
        val isCompleted: Boolean
    )
}
