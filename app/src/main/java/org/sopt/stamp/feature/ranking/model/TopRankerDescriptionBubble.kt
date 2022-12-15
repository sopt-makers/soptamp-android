package org.sopt.stamp.feature.ranking.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.style.Mint300
import org.sopt.stamp.designsystem.style.Pink300
import org.sopt.stamp.designsystem.style.Purple300

enum class TopRankerDescriptionBubble(
    private val rank: Int,
    @DrawableRes val background: Int,
    val backgroundColor: Color,
    val textColor: Color
) {
    FIRST(
        rank = 1,
        background = R.drawable.bubble_middle,
        backgroundColor = Purple300,
        textColor = Color.White
    ),
    SECOND(
        rank = 2,
        background = R.drawable.bubble_left,
        backgroundColor = Pink300,
        textColor = Color.White
    ),
    THIRD(
        rank = 3,
        background = R.drawable.bubble_right,
        backgroundColor = Mint300,
        textColor = Color.Black
    );

    fun hasRankOf(rank: Int): Boolean {
        return this.rank == rank
    }

    companion object {
        fun findBubbleByRank(rank: Int): TopRankerDescriptionBubble = values().find { it.hasRankOf(rank) }
            ?: throw IllegalStateException("$rank 는 상위 3위에 포함된 랭킹이 아닙니다.")
    }
}
