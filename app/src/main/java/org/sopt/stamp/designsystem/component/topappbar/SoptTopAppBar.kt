package org.sopt.stamp.designsystem.component.topappbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.button.SoptampIconButton
import org.sopt.stamp.designsystem.style.SoptTheme

/**
 * 기본적인 TopAppBar 를 디자인에 맞춰 커스텀 하였습니다.
 * 일반적인 TopAppBar 와 다르지 않게 사용할 수 있습니다.
 *
 * @param title 앱바에서 보일 Title 입니다.
 * @param modifier 앱바 내부 content 의 Modifier 를 설정합니다.
 * @param navigationIcon 앱바 내부 navigationIcon 을 설정합니다.
 * @param actions 앱바에서 가능한 액션 버튼 들을 설정합니다.
 * @param backgroundColor 앱바의 backgroundColor 를 설정합니다.
 * @param elevation 앱바의 elevation 을 설정합니다.
 *
 * @author jinsu4755*/

@Composable
fun SoptTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (RowScope.() -> Unit)? = null,
    dropDownButton: @Composable (RowScope.() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    backgroundColor: Color = Color.Transparent,
    elevation: Dp = 0.dp
) {
    SoptAppBar(
        backgroundColor = backgroundColor,
        elevation = elevation,
        modifier = modifier.padding(
            horizontal = SoptAppBarDefault.appBarDefaultHorizontalPadding,
            vertical = SoptAppBarDefault.appBarDefaultVerticalPadding
        )
    ) {
        if (navigationIcon != null) {
            navigationIcon()
            Spacer(modifier = Modifier.size(2.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            title()
            if (dropDownButton != null) {
                Spacer(modifier = Modifier.size(2.dp))
                Row(content = dropDownButton)
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }
}

@Composable
fun SoptAppBar(
    backgroundColor: Color,
    elevation: Dp,
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(SoptAppBarDefault.height),
        color = backgroundColor,
        elevation = elevation
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSoptTopBarOnlyMissionTitleAndDropDownMenu() {
    SoptTheme {
        SoptTopAppBar(
            title = { Text(text = "hello") },
            dropDownButton = {
                SoptampIconButton(imageVector = ImageVector.vectorResource(id = R.drawable.setting))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSoptTopBarTitleWithNavigationButton() {
    SoptTheme {
        SoptTopAppBar(
            title = { Text(text = "hello") },
            navigationIcon = {
                SoptampIconButton(imageVector = ImageVector.vectorResource(id = R.drawable.setting))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSoptTopBarTitleWithNavigationButtonAndActions() {
    SoptTheme {
        SoptTopAppBar(
            title = { Text(text = "hello") },
            navigationIcon = {
                SoptampIconButton(
                    imageVector = ImageVector.vectorResource(id = R.drawable.setting)
                )
            },
            actions = {
                SoptampIconButton(
                    imageVector = ImageVector.vectorResource(id = R.drawable.setting)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSoptTopBarTitleWithNavigationButtonAndActionText() {
    SoptTheme {
        SoptTopAppBar(
            title = { Text(text = "hello") },
            navigationIcon = {
                SoptampIconButton(
                    imageVector = ImageVector.vectorResource(id = R.drawable.setting)
                )
            },
            actions = {
                Text(
                    modifier = Modifier.clickable {},
                    text = "취소"
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        )
    }
}
