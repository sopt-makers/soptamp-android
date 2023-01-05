package org.sopt.stamp.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ModifyStampResponse(
    val stampId: Int,
)
