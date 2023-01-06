package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.ranking.model.RankerUiModel
import org.sopt.stamp.feature.ranking.model.TopRankerDescriptionBubble

@Composable
fun TopRankerList(
    topRanker: Triple<RankerUiModel, RankerUiModel, RankerUiModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 10.dp,
                start = 9.dp,
                end = 9.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var onClickRankerState by remember { mutableStateOf(topRanker.first.rank) }
        var onClickRankerDescriptionState by remember {
            mutableStateOf(topRanker.first.description ?: RankerUiModel.DEFAULT_DESCRIPTION)
        }
        if (onClickRankerState > 0) {
            TopRankDescriptionBubble(
                TopRankerDescriptionBubble.findBubbleByRank(onClickRankerState),
                onClickRankerDescriptionState
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                20.dp,
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.Bottom
        ) {
            val onClickRanker = { rank: Int, description: String ->
                onClickRankerState = rank
                onClickRankerDescriptionState = description
            }
            TopRankerItem(ranker = topRanker.second, 110.dp, onClickRanker)
            TopRankerItem(ranker = topRanker.first, 150.dp, onClickRanker)
            TopRankerItem(ranker = topRanker.third, 70.dp, onClickRanker)
        }
    }
}

@Composable
fun TopRankDescriptionBubble(bubble: TopRankerDescriptionBubble, onClickRankerDescriptionState: String) {
    Box {
        Icon(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = bubble.background),
            contentDescription = "Top Ranker DescriptionBubble",
            tint = bubble.backgroundColor
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = 7.dp,
                bottom = 17.dp,
                start = 34.dp,
                end = 34.dp
            ),
            text = onClickRankerDescriptionState,
            style = SoptTheme.typography.sub3,
            color = bubble.textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewTopRankerList() {
    SoptTheme {
        TopRankerList(
            topRanker = Triple(
                RankerUiModel(
                    rank = 1,
                    userId = 1,
                    nickname = "jinsu",
                    score = 1000

                ),
                RankerUiModel(
                    rank = 2,
                    userId = 1,
                    nickname = "jinsu",
                    score = 900
                ),
                RankerUiModel(
                    rank = 3,
                    userId = 1,
                    nickname = "jinsu",
                    score = 800
                )
            )
        )
    }
}
