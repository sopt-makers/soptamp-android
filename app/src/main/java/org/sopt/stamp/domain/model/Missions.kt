package org.sopt.stamp.domain.model

data class Missions(
    val missions: List<Mission>
) {
    data class Mission(
        val id: Int,
        val title: String,
        val level: Int,
        val profileImage: List<String>,
        val isCompleted: Boolean
    )
}
