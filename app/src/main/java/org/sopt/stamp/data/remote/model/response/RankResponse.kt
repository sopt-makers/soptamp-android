package org.sopt.stamp.data.remote.model.response

@kotlinx.serialization.Serializable
data class RankResponse(
    val rank: Int,
    val userId: Int,
    val nickname: String,
    val point: Int,
    val profileMessage: String?
)
