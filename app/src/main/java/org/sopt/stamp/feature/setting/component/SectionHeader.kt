package org.sopt.stamp.feature.setting.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        text = title,
        style = SoptTheme.typography.caption1,
        color = SoptTheme.colors.onSurface50,
        modifier = modifier.padding(bottom = 4.dp)
    )
}
