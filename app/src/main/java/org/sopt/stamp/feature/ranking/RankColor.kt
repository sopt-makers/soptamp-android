package org.sopt.stamp.feature.ranking

import androidx.compose.runtime.Composable
import org.sopt.stamp.designsystem.style.SoptTheme

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