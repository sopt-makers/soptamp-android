package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.launch
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.MissionNavGraph
import org.sopt.stamp.designsystem.component.button.SoptampFloatingButton
import org.sopt.stamp.designsystem.component.button.SoptampIconButton
import org.sopt.stamp.designsystem.component.dialog.NetworkErrorDialog
import org.sopt.stamp.designsystem.component.layout.LoadingScreen
import org.sopt.stamp.designsystem.component.topappbar.SoptTopAppBar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.destinations.MissionListScreenDestination
import org.sopt.stamp.feature.destinations.UserMissionListScreenDestination
import org.sopt.stamp.feature.ranking.model.RankerNavArg
import org.sopt.stamp.feature.ranking.model.RankerUiModel
import org.sopt.stamp.feature.ranking.model.RankingListUiModel
import org.sopt.stamp.feature.ranking.model.toArgs
import org.sopt.stamp.util.toPx

@MissionNavGraph
@Destination("ranking")
@Composable
fun RankingScreen(
    rankingViewModel: RankingViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>,
    navigator: DestinationsNavigator,
) {
    val state by rankingViewModel.state.collectAsState()
    SoptTheme {
        when (state) {
            RankingState.Loading -> LoadingScreen()
            RankingState.Failure -> NetworkErrorDialog {
                rankingViewModel.fetchRanking()
            }

            is RankingState.Success -> RankingScreen(
                rankingListUiModel = (state as RankingState.Success).uiModel,
                userId = (state as RankingState.Success).userId,
                onClickBack = { resultNavigator.navigateBack() },
                onClickUser = { ranker -> navigator.navigate(UserMissionListScreenDestination(ranker))}
            )
        }
    }
}

@Composable
fun RankingScreen(
    rankingListUiModel: RankingListUiModel,
    userId: Int,
    onClickBack: () -> Unit = {},
    onClickUser: (RankerNavArg) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scrollOffsetPx = (-257).dp.toPx()
    Scaffold(
        topBar = {
            RankingHeader(
                title = "랭킹",
                onClickBack = { onClickBack() }
            )
        },
        floatingActionButton = {
            SoptampFloatingButton(
                text = "내 랭킹 보기"
            ) {
                coroutineScope.launch {
                    val currentUserIndex = rankingListUiModel.otherRankingList.withIndex()
                        .find { it.value.userId == userId }
                        ?.index
                        ?: 0
                    listState.animateScrollToItem(index = currentUserIndex, scrollOffset = scrollOffsetPx)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        val defaultPadding = PaddingValues(
            top = 0.dp,
            bottom = paddingValues.calculateBottomPadding(),
            start = 16.dp,
            end = 16.dp
        )
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(defaultPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 70.dp)
        ) {
            item {
                TopRankerList(
                    topRanker = rankingListUiModel.topRankingList,
                    onClickTopRankerBubble = { ranker -> onClickUser(ranker.toArgs()) }
                )
            }
            items(rankingListUiModel.otherRankingList) { item ->
                RankListItem(
                    item = item,
                    isMyRanking = item.userId == userId,
                    onClickUser = { ranker ->
                        if (userId != ranker.userId) onClickUser(ranker.toArgs())
                    }
                )
            }
        }
    }
}

@Composable
fun RankingHeader(
    title: String,
    onClickBack: () -> Unit = {}
) {
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
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_letf),
                onClick = { onClickBack() }
            )
        }
    )
}

@Preview
@Composable
fun PreviewRankingScreen() {
    val previewRanking = mutableListOf<RankerUiModel>()
    repeat(100) {
        previewRanking.add(
            RankerUiModel(
                rank = it + 1,
                userId = it + 1,
                nickname = "jinsu",
                score = 1000 - (it * 10)
            )
        )
    }
    SoptTheme {
        RankingScreen(RankingListUiModel(previewRanking), 6)
    }
}
