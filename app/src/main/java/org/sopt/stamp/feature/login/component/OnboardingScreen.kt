package org.sopt.stamp.feature.login.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

private enum class OnboardingUiModel(
    val index: Int,
    @DrawableRes val imageResId: Int
) {
    ONBOARDING_1(0, R.drawable.ic_onboarding_1),
    ONBOARDING_2(1, R.drawable.ic_onboarding_2),
    ONBOARDING_3(2, R.drawable.ic_onboarding_3);

    companion object {
        fun idOf(index: Int) = values().find { it.index == index }?.imageResId
            ?: throw IllegalArgumentException("There's no OnboardingUiModel with index: $index")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen() {
    Column {
        HorizontalPager(pageCount = 3) {
            Image(
                painter = painterResource(id = OnboardingUiModel.idOf(it)),
                contentDescription = "OnboardingScreen $it"
            )
        }
    }
}

@DefaultPreview
@Composable
private fun OnboardingPreview() {
    SoptTheme {
        SoptColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            OnboardingScreen()
        }
    }
}
