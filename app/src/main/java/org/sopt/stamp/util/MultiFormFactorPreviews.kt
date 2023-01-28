package org.sopt.stamp.util

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "phone",
    device = "spec:shape=Normal,width=360,height=800,unit=dp,dpi=480",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "tablet",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
    showBackground = true,
    showSystemUi = true
)
annotation class MultiFormFactorPreviews