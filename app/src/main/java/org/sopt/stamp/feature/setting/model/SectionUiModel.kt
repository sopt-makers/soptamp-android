package org.sopt.stamp.feature.setting.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.sopt.stamp.designsystem.style.Gray900

sealed interface SectionUiModel {
    @Immutable
    data class Option(
        val title: String,
        @DrawableRes val optionIconResId: Int = -1,
        val containerModifier: Modifier = Modifier,
        val textColor: Color = Gray900,
        val onPress: () -> Unit = {}
    ) : SectionUiModel

    @Immutable
    data class Header(
        val containerModifier: Modifier,
        val title: String
    ) : SectionUiModel

    @Immutable
    object Spacer : SectionUiModel
}
