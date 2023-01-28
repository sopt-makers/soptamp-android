package org.sopt.stamp.data.remote.model

data class RankData(
    val rank: Int,
    val userId: Int,
    val nickname: String,
    val point: Int,
    val profileMessage: String?
)