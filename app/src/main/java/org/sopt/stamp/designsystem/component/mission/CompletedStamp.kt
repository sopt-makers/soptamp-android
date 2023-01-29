package org.sopt.stamp.designsystem.component.mission

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun CompletedStamp(
    stamp: Stamp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = stamp.lottieImage),
        contentDescription = "Completed Stamp Image",
        modifier = modifier
    )
}