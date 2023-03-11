package org.sopt.stamp.data.remote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(
    @SerialName("password")
    val password: String
)
