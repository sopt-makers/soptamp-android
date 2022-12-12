package org.sopt.stamp.designsystem.component.mission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CompletedStamp(
    stamp: Stamp,
    modifier: Modifier
) {
    val completedStamp by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(stamp.lottie))
    LottieAnimation(
        composition = completedStamp,
        modifier = modifier
    )
}
