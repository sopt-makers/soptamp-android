package org.sopt.stamp.feature.ranking.model

class RankingListUiModel private constructor(
    val topRankingList: Triple<RankingUiModel, RankingUiModel, RankingUiModel>,
    val otherRankingList: List<RankingUiModel>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RankingListUiModel

        if (topRankingList != other.topRankingList) return false
        if (otherRankingList != other.otherRankingList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = topRankingList.hashCode()
        result = 31 * result + otherRankingList.hashCode()
        return result
    }

    override fun toString(): String {
        return "RankingListUiModel(topRankingList=$topRankingList, otherRankingList=$otherRankingList)"
    }

    companion object {
        operator fun invoke(rankingList: List<RankingUiModel>): RankingListUiModel {
            return RankingListUiModel(
                rankingList.getTopRanking(),
                rankingList.getOtherRanking()
            )
        }

        private fun List<RankingUiModel>.getTopRanking(): Triple<RankingUiModel, RankingUiModel, RankingUiModel> {
            val topRankPartition = this.partition { it.isTopRank() }.first.toMutableList()
            if (topRankPartition.size != RankingUiModel.STANDARD_TOP_RANK) {
                repeat(RankingUiModel.STANDARD_TOP_RANK - topRankPartition.size) {
                    topRankPartition.add(RankingUiModel.DEFAULT_RANK)
                }
            }
            return Triple(
                topRankPartition[0],
                topRankPartition[1],
                topRankPartition[2]
            )
        }

        private fun List<RankingUiModel>.getOtherRanking(): List<RankingUiModel> {
            return this.partition { it.isNotTopRank() }
                .second
        }
    }
}
