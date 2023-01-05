package org.sopt.stamp.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ModifyStampResponse(
    val stampId: Int,
)
