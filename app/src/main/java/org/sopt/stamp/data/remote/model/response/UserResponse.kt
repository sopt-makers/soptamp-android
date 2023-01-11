package org.sopt.stamp.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: Int?,
    val message: String?
)
