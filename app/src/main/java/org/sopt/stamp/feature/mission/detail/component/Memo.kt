package org.sopt.stamp.feature.mission.detail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.ranking.getRankTextColor
import org.sopt.stamp.util.DefaultPreview

@Composable
fun Memo(
    value: String,
    onValueChange: (String) -> Unit,
    borderColor: Color
) {
    val isEmpty = remember(value) { value.isEmpty() }

    val modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
        .defaultMinSize(minHeight = 132.dp)
        .clip(RoundedCornerShape(10.dp))

    val modifierWithBorder = if (isEmpty) {
        modifier
    } else {
        modifier.border(
            width = 1.dp,
            color = borderColor,
            shape = RoundedCornerShape(10.dp)
        )
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifierWithBorder,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isEmpty) SoptTheme.colors.onSurface5 else SoptTheme.colors.white,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = SoptTheme.colors.onSurface90,
            placeholderColor = SoptTheme.colors.onSurface60
        ),
        textStyle = SoptTheme.typography.caption1,
        placeholder = {
            Text(
                text = "메모를 작성해 주세요.",
                style = SoptTheme.typography.caption1
            )
        }
    )
}

@DefaultPreview
@Composable
private fun MemoPreview() {
    SoptTheme {
        Memo(
            value = "",
            onValueChange = {},
            borderColor = getRankTextColor(2)
        )
    }
}
