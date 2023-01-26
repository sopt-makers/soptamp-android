package org.sopt.stamp.data.remote.model.response

import kotlinx.serialization.Serializable
import org.sopt.stamp.domain.model.User

@Serializable
data class UserResponse(
    val userId: Int? = -1,
    val message: String? = "",
    val statusCode: Int? = -1,
    val profileMessage: String? = ""
) {
    fun toUser() = User(userId, message, statusCode, profileMessage)
}
