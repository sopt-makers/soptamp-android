package org.sopt.stamp.feature.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.button.SoptampIconButton
import org.sopt.stamp.designsystem.component.util.noRippleClickable
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

@Composable
fun Option(
    modifier: Modifier = Modifier,
    title: String,
    onPress: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .noRippleClickable { onPress() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = SoptTheme.typography.sub1,
            color = SoptTheme.colors.onSurface90
        )
        SoptampIconButton(
            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_right)
        )
    }
}

@DefaultPreview
@Composable
private fun OptionPreview() {
    SoptTheme {
        Option(
            modifier = Modifier,
            title = "비밀번호 변경하기"
        ) {}
    }
}
