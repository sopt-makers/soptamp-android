package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.util.noRippleClickable
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.ranking.model.RankerUiModel

@Composable
fun TopRankerItem(
    ranker: RankerUiModel,
    height: Dp,
    onClick: (RankerUiModel) -> Unit = {}
) {
    Column(
        modifier = Modifier.noRippleClickable { onClick(ranker) },
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopRankBarOfRankText(rank = ranker.rank)
        TopRankBarOfGraph(rank = ranker.rank, score = ranker.score, height = height)
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = ranker.nickname,
            style = SoptTheme.typography.h3,
            color = Color.Black
        )
    }
}

@Composable
fun TopRankBarOfRankText(rank: Int) {
    if (rank == 1) {
        Box {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp),
                painter = painterResource(id = R.drawable.ic_star),
                tint = SoptTheme.colors.purple100,
                contentDescription = "RankText Star Icon"
            )
            RankNumber(modifier = Modifier.align(Alignment.Center), rank = rank)
        }
        Spacer(modifier = Modifier.size(13.dp))
    } else {
        RankNumber(rank = rank)
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
fun TopRankBarOfGraph(
    rank: Int,
    score: Int,
    height: Dp
) {
    Box(
        modifier = Modifier
            .size(90.dp, height)
            .background(
                color = getRankBackgroundColor(rank),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        RankScore(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
            rank = rank,
            score = score
        )
    }
}

@Preview
@Composable
fun PreviewTopRankerItem() {
    SoptTheme {
        TopRankerItem(
            ranker = RankerUiModel(
                rank = 1,
                userId = 1,
                nickname = "jinsu",
                score = 1000
            ),
            height = 150.dp
        )
    }
}