package org.sopt.stamp.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionResponse(
    val id: Int,
    val title: String,
    val level: Int,
    val profileImage: List<String>?,
    val isCompleted: Boolean
)