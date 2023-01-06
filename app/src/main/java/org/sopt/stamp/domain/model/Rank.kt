package org.sopt.stamp.domain.model

data class Rank(
    val rank: Int,
    val userId: Int,
    val nickname: String,
    val point: Int,
    val profileMessage: String?
)
