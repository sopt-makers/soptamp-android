package org.sopt.stamp.feature.mission.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.ratingbar.RatingBar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.ranking.getRankTextColor
import org.sopt.stamp.util.DefaultPreview

@Composable
fun Header(
    title: String,
    stars: Int
) {
    Surface(
        modifier = Modifier
            .background(
                color = SoptTheme.colors.onSurface5,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.background(SoptTheme.colors.onSurface5),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RatingBar(
                icon = R.drawable.ic_star,
                stars = stars,
                selectedColor = getRankTextColor(rank = stars)
            )
            Text(
                text = title,
                style = SoptTheme.typography.sub1,
                color = SoptTheme.colors.onSurface90
            )
        }
    }
}

@DefaultPreview
@Composable
private fun HeaderPreview() {
    SoptTheme {
        Header(title = "오늘은 무슨 일을 할까?", stars = 2)
    }
}
