package org.sopt.stamp.designsystem.component.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.style.SoptTheme

@Composable
fun SoptampFloatingButton(
    text: String,
    onClick: () -> Unit = {}
) {
    ExtendedFloatingActionButton(
        text = {
            Text(
                text = text,
                color = Color.White,
                style = SoptTheme.typography.h2
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.trophy),
                contentDescription = "Extended Floating Action Button Trophy Icon",
                tint = Color.White
            )
        },
        onClick = { onClick() },
        shape = RoundedCornerShape(46.dp),
        backgroundColor = SoptTheme.colors.purple300
    )
}