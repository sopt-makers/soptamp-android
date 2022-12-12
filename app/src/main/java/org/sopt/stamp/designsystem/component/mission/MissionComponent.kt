package org.sopt.stamp.designsystem.component.mission

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.component.mission.model.MissionUiModel
import org.sopt.stamp.designsystem.component.util.noRippleClickable
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.MissionLevel

@Composable
fun MissionComponent(
    mission: MissionUiModel,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) = {}
) {
    val shape = MissionShape.DEFAULT_WAVE
    val stamp = Stamp.findStampByLevel(mission.level)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier.defaultMinSize(160.dp, 200.dp).background(
                color = if (mission.isCompleted) stamp.background else SoptTheme.colors.onSurface5,
                shape = shape
            ).noRippleClickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (mission.isCompleted) {
                CompletedStamp(
                    stamp = stamp,
                    modifier = Modifier.size(104.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
            } else {
                LevelOfMission(stamp = stamp, spaceSize = 10.dp)
                Spacer(modifier = Modifier.size(16.dp))
            }
            TitleOfMission(missionTitle = mission.title)
        }
    }
}

@Composable
private fun TitleOfMission(missionTitle: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = missionTitle,
            style = SoptTheme.typography.sub3,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth(0.8875f)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun PreviewMissionComponent() {
    SoptTheme {
        val previewMission = MissionUiModel(
            id = 1,
            title = "일이삼사오육칠팔구십일일이삼사오육칠팔구십일",
            level = MissionLevel.of(1),
            isCompleted = !false
        )
        MissionComponent(
            modifier = Modifier.width(160.dp),
            mission = previewMission
        )
    }
}
