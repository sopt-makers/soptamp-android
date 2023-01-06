package org.sopt.stamp.data.mapper // ktlint-disable filename

import org.sopt.stamp.data.remote.model.RankData
import org.sopt.stamp.domain.model.Rank

internal fun List<RankData>.toDomain(): List<Rank> = this.map {
    it.toDomain()
}

internal fun RankData.toDomain(): Rank = Rank(
    rank,
    userId,
    nickname,
    point,
    profileMessage
)
