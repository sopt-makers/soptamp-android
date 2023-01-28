package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.sopt.stamp.designsystem.style.MontserratBold
import org.sopt.stamp.designsystem.style.SoptTheme

@Composable
fun RankNumber(
    modifier: Modifier = Modifier,
    rank: Int,
    isMyRankNumber: Boolean = false
) {
    val defaultRankSymbols = "-"
    Text(
        text = if (rank <= 0) defaultRankSymbols else "$rank",
        fontFamily = MontserratBold,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = if (isMyRankNumber) SoptTheme.colors.purple300 else getRankTextColor(rank),
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewRankNumber() {
    SoptTheme {
        Column {
            RankNumber(rank = 1)
            RankNumber(rank = 2)
            RankNumber(rank = 3)
            RankNumber(rank = 4)
        }
    }
}