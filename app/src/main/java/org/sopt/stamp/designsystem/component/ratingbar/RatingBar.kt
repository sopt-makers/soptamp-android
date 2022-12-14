package org.sopt.stamp.designsystem.component.ratingbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.MultiFormFactorPreviews

private const val DEFAULT_MAX = 3

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    maxStars: Int = DEFAULT_MAX,
    stars: Int,
    gapSize: Dp = 10.dp,
    selectedColor: Color = SoptTheme.colors.mint300,
    unselectedColor: Color = SoptTheme.colors.onSurface30,
) {
    require(maxStars >= stars) {
        "RatingBar의 최대 별 갯수는 선택된 별 갯수보다 커야합니다. Max Stars: $maxStars, stars: $stars"
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(gapSize)
    ) {
        repeat(maxStars) {
            val ordinalIndex = it + 1
            RatingIcon(
                icon = icon,
                tint = if (ordinalIndex > stars) unselectedColor else selectedColor
            )
        }
    }
}

@Composable
private fun RatingIcon(
    @DrawableRes icon: Int,
    tint: Color
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Rating Icon",
        tint = tint
    )
}

@MultiFormFactorPreviews
@Composable
private fun RatingBarPreview() {
    SoptTheme {
        Box(
            contentAlignment = Alignment.Center
        ) {
            RatingBar(
                icon = R.drawable.ic_star,
                maxStars = 3,
                stars = 2,
                gapSize = 10.dp,
            )
        }
    }
}
