package org.sopt.stamp.data.remote.mapper // ktlint-disable filename

import org.sopt.stamp.data.remote.model.RankData
import org.sopt.stamp.data.remote.model.response.RankResponse

internal fun List<RankResponse>.toData(): List<RankData> = this.map {
    it.toData()
}

internal fun RankResponse.toData(): RankData = RankData(
    rank,
    userId,
    nickname,
    point,
    profileMessage
)