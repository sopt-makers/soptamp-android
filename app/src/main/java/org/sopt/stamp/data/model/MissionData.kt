package org.sopt.stamp.data.model

data class MissionData(
    val id: Int,
    val title: String,
    val level: Int,
    val profileImage: List<String>?,
    val isCompleted: Boolean
)
