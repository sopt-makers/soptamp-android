package org.sopt.stamp.designsystem.component.button

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.util.noRippleClickable
import org.sopt.stamp.designsystem.style.SoptTheme

/**
 * 앱 디자인 시스템 Icon Button 입니다.
 * Icon에 들어갈 ImageVector 는 32*32로 통일된 크기를 가정합니다.
 *
 * @param imageVector [ImageVector] to draw inside this Icon
 *
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes,
 * and does not represent a meaningful action that a user can take. This text should be
 * localized, such as by using [androidx.compose.ui.res.stringResource] or similar
 *
 * @param tint tint to be applied to [imageVector]. If [Color.Unspecified] is provided, then no
 *  tint is applied
 *
 * @author jinsu4755
 * */
@Composable
fun SoptampIconButton(
    imageVector: ImageVector,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    onClick: (() -> Unit) = {}
) {
    Row(
        modifier = Modifier.noRippleClickable { onClick() }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBarIconButton() {
    SoptTheme {
        SoptampIconButton(
            imageVector = ImageVector.vectorResource(id = R.drawable.up_expand)
        )
    }
}
