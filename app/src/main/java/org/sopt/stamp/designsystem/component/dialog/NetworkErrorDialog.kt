package org.sopt.stamp.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.component.util.noRippleClickable
import org.sopt.stamp.designsystem.style.SoptTheme

@Composable
fun NetworkErrorDialog(
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onRetry() },
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "네트워크가 원활하지 않습니다.",
                        style = SoptTheme.typography.sub1,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.size(13.dp))
                    Text(
                        text = "인터넷 연결을 확인하고 다시 시도해 주세요.",
                        style = SoptTheme.typography.caption3,
                        color = SoptTheme.colors.onSurface50
                    )
                    Spacer(modifier = Modifier.size(28.dp))
                }
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color(0xFFFF8080))
                    .noRippleClickable { onRetry() }
                    .padding(vertical = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "확인",
                    style = SoptTheme.typography.sub1,
                    color = Color.White
                )
            }
        }
    )
}
