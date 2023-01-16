package org.sopt.stamp.feature.ranking

import org.sopt.stamp.feature.ranking.model.RankingListUiModel

sealed class RankingState {
    object Loading : RankingState()

    data class Success(val uiModel: RankingListUiModel, val userId: Int) : RankingState()

    object Failure : RankingState()
}
