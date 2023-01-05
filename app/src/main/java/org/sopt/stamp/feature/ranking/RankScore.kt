package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.sopt.stamp.designsystem.style.MontserratRegular
import org.sopt.stamp.designsystem.style.PretendardMedium
import org.sopt.stamp.designsystem.style.SoptTheme

@Composable
fun RankScore(
    modifier: Modifier = Modifier,
    rank: Int,
    score: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "$score",
            fontFamily = MontserratRegular,
            fontSize = 30.sp,
            fontWeight = FontWeight.W400,
            color = getRankTextColor(rank)
        )
        Text(
            text = "점",
            fontFamily = PretendardMedium,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            color = getRankTextColor(rank)
        )
    }
}

@Preview
@Composable
fun PreviewRankScore() {
    SoptTheme {
        RankScore(rank = 1, score = 1000)
    }
}
