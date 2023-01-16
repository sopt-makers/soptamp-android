package org.sopt.stamp.feature.ranking.model

import org.sopt.stamp.domain.model.Rank

data class RankerUiModel(
    val rank: Int,
    val userId: Int,
    val nickname: String,
    val description: String? = null,
    val score: Int
) {
    fun isTopRank() = (rank <= STANDARD_TOP_RANK)
    fun isNotTopRank() = (rank > STANDARD_TOP_RANK)

    companion object {
        private const val DEFAULT_USER_NAME = "-"
        const val STANDARD_TOP_RANK = 3
        const val DEFAULT_DESCRIPTION = "설정된 한 마디가 없습니다"
        val DEFAULT_RANK = RankerUiModel(0, 1, DEFAULT_USER_NAME, null, 0)
    }
}

fun List<Rank>.toUiModel(): RankingListUiModel = RankingListUiModel(
    this.map { it.toUiModel() }
)

fun Rank.toUiModel(): RankerUiModel = RankerUiModel(
    rank = this.rank,
    userId = this.userId,
    nickname = this.nickname,
    description = this.profileMessage,
    score = this.point
)
