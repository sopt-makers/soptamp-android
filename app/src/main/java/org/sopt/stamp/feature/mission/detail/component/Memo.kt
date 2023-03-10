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
import org.sopt.stamp.designsystem.style.Gray50
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.designsystem.style.White
import org.sopt.stamp.feature.ranking.getRankTextColor
import org.sopt.stamp.util.DefaultPreview

@Composable
fun Memo(
    value: String,
    onValueChange: (String) -> Unit,
    borderColor: Color,
    isEditable: Boolean
) {
    val isEmpty = remember(value) { value.isEmpty() }

    val modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
        .defaultMinSize(minHeight = 132.dp)
        .clip(RoundedCornerShape(10.dp))

    val modifierWithBorder = remember(isEmpty, isEditable) {
        if (isEmpty || !isEditable) {
            modifier
        } else {
            modifier.border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
        }
    }

    val backgroundColor = remember(isEmpty, isEditable) {
        if (isEmpty || !isEditable) {
            Gray50
        } else {
            White
        }
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifierWithBorder,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = SoptTheme.colors.onSurface90,
            placeholderColor = SoptTheme.colors.onSurface60
        ),
        textStyle = SoptTheme.typography.caption1,
        placeholder = {
            Text(
                text = "메모를 작성해 주세요.",
                style = SoptTheme.typography.caption1
            )
        },
        enabled = isEditable
    )
}

@DefaultPreview
@Composable
private fun MemoPreview() {
    SoptTheme {
        Memo(
            value = "안돼",
            onValueChange = {},
            borderColor = getRankTextColor(2),
            true
        )
    }
}
