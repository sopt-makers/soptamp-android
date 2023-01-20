package org.sopt.stamp.data.remote.model.response

import kotlinx.serialization.Serializable
import org.sopt.stamp.domain.model.User

@Serializable
data class UserResponse(
    val userId: Int?,
    val message: String?,
    val statusCode: Int?
) {
    fun toUser() = User(userId, message, statusCode)
}
