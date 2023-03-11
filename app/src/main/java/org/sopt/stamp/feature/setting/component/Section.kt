package org.sopt.stamp.feature.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.style.Gray50
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.setting.model.SectionUiModel
import org.sopt.stamp.util.DefaultPreview

@Composable
fun Section(
    items: List<SectionUiModel>
) {
    Column(
        modifier = Modifier
            .background(
                color = SoptTheme.colors.onSurface5,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        items.forEach {
            when (it) {
                is SectionUiModel.Header -> {
                    Header(
                        modifier = it.containerModifier,
                        title = it.title
                    )
                }

                is SectionUiModel.Option -> {
                    Option(
                        modifier = it.containerModifier,
                        title = it.title,
                        titleTextColor = it.textColor,
                        iconResId = it.optionIconResId,
                        onPress = it.onPress
                    )
                }

                is SectionUiModel.Spacer -> {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(SoptTheme.colors.onSurface10)
                    )
                }
            }
        }
    }
}

@DefaultPreview
@Composable
private fun SectionPreview() {
    val sectionItems = listOf(
        SectionUiModel.Header(
            containerModifier = Modifier
                .background(
                    color = Gray50,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(horizontal = 16.dp),
            title = "내 정보"
        ),
        SectionUiModel.Option(
            title = "한 마디 편집",
            optionIconResId = R.drawable.arrow_right,
            containerModifier = Modifier
                .background(Gray50)
                .padding(horizontal = 16.dp)
        ),
        SectionUiModel.Spacer,
        SectionUiModel.Option(
            title = "한 마디 편집",
            optionIconResId = R.drawable.arrow_right,
            containerModifier = Modifier
                .background(Gray50)
                .padding(horizontal = 16.dp)
        ),
        SectionUiModel.Spacer,
        SectionUiModel.Option(
            title = "한 마디 편집",
            optionIconResId = R.drawable.arrow_right,
            containerModifier = Modifier
                .background(
                    color = Gray50,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(horizontal = 16.dp)
        ),
    )
    SoptTheme {
        Column(
            modifier = Modifier
                .background(SoptTheme.colors.white)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Section(items = sectionItems)
        }
    }
}
