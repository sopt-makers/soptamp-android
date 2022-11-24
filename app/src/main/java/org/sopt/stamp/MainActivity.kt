package org.sopt.stamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.MultiFormFactorPreviews

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoptTheme {
                Greeting()
            }
        }
    }
}

@Composable
private fun Greeting() {
    Box {
        Text("Hello World!")
    }
}

@MultiFormFactorPreviews
@Composable
fun Preview() {
    SoptTheme {
        Greeting()
    }
}
