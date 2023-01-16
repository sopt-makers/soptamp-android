package org.sopt.stamp.feature.ranking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.stamp.designsystem.style.SoptTheme

@AndroidEntryPoint
class RankingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoptTheme {
                RankingScreen()
            }
        }
    }
}
