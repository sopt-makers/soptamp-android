package org.sopt.stamp.feature.ranking.model

data class RankingUiModel(
    val rank: Int,
    val user: String,
    val _description: String? = null,
    val score: Int
) {
    fun isTopRank() = (rank <= STANDARD_TOP_RANK)
    fun isNotTopRank() = (rank > STANDARD_TOP_RANK)

    val description = _description ?: "설정된 한 마디가 없습니다"

    companion object {
        private const val DEFAULT_USER_NAME = "-"
        const val STANDARD_TOP_RANK = 3
        val DEFAULT_RANK = RankingUiModel(0, DEFAULT_USER_NAME, null, 0)
    }
}
