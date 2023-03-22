package org.sopt.stamp.feature.login.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

@Composable
fun OnboardingPage(
    @DrawableRes image: Int,
    title: String,
    content: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(id = image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(16.dp))
        OnboardingPageTitle(text = title)
        Spacer(modifier = Modifier.size(12.dp))
        OnboardingPageContent(text = content)
    }
}

@Composable
fun OnboardingPageTitle(text: String) {
    Text(
        text = text,
        style = SoptTheme.typography.h1,
        color = SoptTheme.colors.onSurface90
    )
}

@Composable
fun OnboardingPageContent(text: String) {
    Text(
        text = text,
        style = SoptTheme.typography.sub2,
        color = SoptTheme.colors.onSurface50
    )
}

@DefaultPreview
@Composable
private fun OnboardingPreview() {
    SoptTheme {
        SoptColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            OnboardingPage(
                R.drawable.ic_onboarding_1,
                "title",
                "content"
            )
        }
    }
}
