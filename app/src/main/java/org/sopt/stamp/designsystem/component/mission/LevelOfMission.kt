package org.sopt.stamp.designsystem.component.mission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import org.sopt.stamp.R
import org.sopt.stamp.domain.MissionLevel

@Composable
fun LevelOfMission(stamp: Stamp, spaceSize: Dp) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spaceSize)
    ) {
        MissionLevelOfStar(stamp = stamp)
    }
}

@Composable
private fun MissionLevelOfStar(stamp: Stamp) {
    (MissionLevel.MINIMUM_LEVEL..MissionLevel.MAXIMUM_LEVEL).forEach {
        val starColor = if (it <= stamp.missionLevel.value) {
            stamp.starColor
        } else {
            Stamp.defaultStarColor
        }
        Icon(
            painter = painterResource(id = R.drawable.level_star),
            contentDescription = "Star Of Mission Level",
            tint = starColor
        )
    }
}