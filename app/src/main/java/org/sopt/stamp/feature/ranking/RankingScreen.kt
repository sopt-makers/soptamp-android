package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.button.SoptampFloatingButton
import org.sopt.stamp.designsystem.component.button.SoptampIconButton
import org.sopt.stamp.designsystem.component.topappbar.SoptTopAppBar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.ranking.model.RankerUiModel
import org.sopt.stamp.feature.ranking.model.RankingListUiModel

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
            modifier = Modifier.padding(defaultPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 70.dp)
        ) {
            item { TopRankerList(rankingListUiModel.topRankingList) }
            items(rankingListUiModel.otherRankingList) { item ->
                RankListItem(item)
            }
        }
    }
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

@Preview
@Composable
fun PreviewRankingScreen() {
    val previewRanking = mutableListOf<RankerUiModel>()
    repeat(100) {
        previewRanking.add(
            RankerUiModel(
                rank = it + 1,
                userId = 1,
                nickname = "jinsu",
                score = 1000 - (it * 10)
            )
        )
    }
    SoptTheme {
        RankingScreen(RankingListUiModel(previewRanking))
    }
}
