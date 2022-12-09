package org.sopt.stamp.designsystem.component.toolbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.style.SoptTheme
import java.lang.IllegalArgumentException

enum class ToolbarIconType(
    @DrawableRes private val resId: Int = -1,
) {
    NONE,
    BACK(R.drawable.ic_back);

    companion object {
        fun getIdFrom(type: ToolbarIconType) = values().find { it.name == type.name }?.resId
            ?: throw IllegalArgumentException("There's no icon in this class. Icon: ${type.name}")
    }
}

@Composable
fun Toolbar(
    modifier: Modifier,
    title: String = "",
    iconOption: ToolbarIconType = ToolbarIconType.NONE,
    onBack: () -> Unit = {},
    onPressIcon: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back Button",
                modifier = Modifier.clickable(onClick = onBack)
            )
            Text(
                text = title,
                style = SoptTheme.typography.h1,
                color = SoptTheme.colors.onSurface
            )
        }

        if (iconOption != ToolbarIconType.NONE) {
            Image(
                painter = painterResource(id = ToolbarIconType.getIdFrom(iconOption)),
                contentDescription = "Option Menu Icon"
            )
        }
    }
}
