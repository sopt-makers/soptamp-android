package org.sopt.stamp.feature.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.ranking.model.RankerUiModel

@Composable
fun RankListItem(item: RankerUiModel) {
    val itemPadding = PaddingValues(
        top = 19.dp,
        bottom = 16.dp,
        start = 15.dp,
        end = 15.dp
    )
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = SoptTheme.colors.onSurface5,
                shape = RoundedCornerShape(8.dp)
            ).padding(itemPadding),
        horizontalArrangement = Arrangement.Center
    ) {
        RankNumber(
            modifier = Modifier.padding(horizontal = 17.dp),
            rank = item.rank
        )
        Spacer(modifier = Modifier.size(16.dp))
        RankerInformation(
            modifier = Modifier.weight(1f),
            user = item.user,
            description = item.description
        )
        Spacer(modifier = Modifier.size(12.dp))
        RankScore(rank = item.rank, score = item.score)
    }
}

@Composable
fun RankerInformation(
    modifier: Modifier = Modifier,
    user: String,
    description: String?
) {
    Column(modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = user,
            style = SoptTheme.typography.h3,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description ?: RankerUiModel.DEFAULT_DESCRIPTION,
            style = SoptTheme.typography.caption1,
            color = SoptTheme.colors.onSurface70,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun PreviewRankListItem() {
    SoptTheme {
        RankListItem(
            item = RankerUiModel(
                rank = 4,
                user = "????????????????????????????????????????????????????????????",
                description = "????????????????????????????????????????????????????????????",
                score = 300
            )
        )
    }
}
