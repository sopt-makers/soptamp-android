package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.button.SoptampFloatingButton
import org.sopt.stamp.designsystem.component.button.SoptampIconButton
import org.sopt.stamp.designsystem.component.topappbar.SoptTopAppBar
import org.sopt.stamp.designsystem.component.util.noRippleClickable
import org.sopt.stamp.designsystem.style.MontserratBold
import org.sopt.stamp.designsystem.style.MontserratRegular
import org.sopt.stamp.designsystem.style.PretendardMedium
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.ranking.model.RankingListUiModel
import org.sopt.stamp.feature.ranking.model.RankingUiModel
import org.sopt.stamp.feature.ranking.model.TopRankerDescriptionBubble

@Composable
fun RankingScreen(
    rankingListUiModel: RankingListUiModel
) {
    Scaffold(
        topBar = { RankingHeader(title = "랭킹") },
        floatingActionButton = { SoptampFloatingButton(text = "내 랭킹 보기") },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        val defaultPadding = PaddingValues(
            top = 0.dp,
            bottom = paddingValues.calculateBottomPadding(),
            start = 16.dp,
            end = 16.dp
        )
        LazyColumn(
            modifier = Modifier.padding(defaultPadding)
        ) {
            item { TopRanker(rankingListUiModel.topRankingList) }
            items(rankingListUiModel.otherRankingList) {
            }
        }
    }
}

@Composable
fun RankerListItem() {
}

@Composable
fun RankingHeader(title: String) {
    SoptTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.Black,
                style = SoptTheme.typography.h2
            )
        },
        navigationIcon = {
            SoptampIconButton(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_letf)
            )
        }
    )
}

@Composable
fun TopRanker(
    topRanker: Triple<RankingUiModel, RankingUiModel, RankingUiModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 20.dp,
                start = 9.dp,
                end = 9.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var onClickRankerState by remember { mutableStateOf(topRanker.first.rank) }
        val onClickRankerDescriptionState by remember { mutableStateOf(topRanker.first.description) }
        if (onClickRankerState > 0) {
            TopRankDescriptionBubble(
                TopRankerDescriptionBubble.findBubbleByRank(onClickRankerState),
                onClickRankerDescriptionState
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                20.dp,
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.Bottom
        ) {
            TopRankBar(ranker = topRanker.second, 110.dp)
            TopRankBar(ranker = topRanker.first, 150.dp)
            TopRankBar(ranker = topRanker.third, 70.dp)
        }
    }
}

@Composable
fun TopRankDescriptionBubble(bubble: TopRankerDescriptionBubble, onClickRankerDescriptionState: String) {
    Box {
        Icon(
            painter = painterResource(id = bubble.background),
            contentDescription = "Top Ranker DescriptionBubble",
            tint = bubble.backgroundColor
        )
    }
}

@Composable
fun TopRankBar(
    ranker: RankingUiModel,
    height: Dp
) {
    val (rank, user, description, score) = ranker
    Column(
        modifier = Modifier.noRippleClickable { },
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopRankBarOfRankText(rank = rank)
        TopRankBarOfGraph(rank = rank, score = score, height = height)
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = user,
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
            RankText(modifier = Modifier.align(Alignment.Center), rank = rank)
        }
        Spacer(modifier = Modifier.size(13.dp))
    } else {
        RankText(rank = rank)
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
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
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
}

@Composable
fun RankText(
    modifier: Modifier = Modifier,
    rank: Int
) {
    val defaultRankSymbols = "-"
    Text(
        text = if (rank <= 0) defaultRankSymbols else "$rank",
        fontFamily = MontserratBold,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = getRankTextColor(rank),
        modifier = modifier
    )
}

@Composable
fun getRankTextColor(rank: Int) = when (rank) {
    1 -> SoptTheme.colors.purple300
    2 -> SoptTheme.colors.pink300
    3 -> SoptTheme.colors.mint300
    else -> SoptTheme.colors.onSurface50
}

@Composable
fun getRankBackgroundColor(rank: Int) = when (rank) {
    1 -> SoptTheme.colors.purple200
    2 -> SoptTheme.colors.pink200
    3 -> SoptTheme.colors.mint200
    else -> SoptTheme.colors.onSurface5
}

@Preview
@Composable
fun PreviewRankingScreen() {
    val previewRanking = mutableListOf<RankingUiModel>()
    repeat(100) {
        previewRanking.add(
            RankingUiModel(
                rank = it + 1,
                user = "jinsu",
                score = 1000 - (it * 10)
            )
        )
    }
    SoptTheme {
        RankingScreen(RankingListUiModel(previewRanking))
    }
}
