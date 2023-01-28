package org.sopt.stamp.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val nickname: String,
    val email: String,
    val password: String
)