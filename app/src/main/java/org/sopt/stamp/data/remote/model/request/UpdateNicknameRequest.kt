package org.sopt.stamp.data.remote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNicknameRequest(
    @SerialName("nickname")
    val nickname: String
)
