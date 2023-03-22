/*
 * Copyright 2023 SOPT - Shout Our Passion Together
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sopt.stamp.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

@Composable
fun SoptampButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = SoptTheme.colors.purple300
    ),
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    shape: Shape = RoundedCornerShape(9.dp),
    elevation: ButtonElevation = ButtonDefaults.elevation(0.dp, 0.dp),
    onClick: () -> Unit = {},
    isEnable: Boolean = true
) {
    Button(
        modifier = modifier,
        contentPadding = contentPadding,
        onClick = { onClick() },
        shape = shape,
        colors = buttonColors,
        elevation = elevation,
        enabled = isEnable
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@DefaultPreview
@Composable
fun SoptampButtonPreview() {
    SoptTheme {
        SoptampButton(
            text = "Button",
            textStyle = SoptTheme.typography.sub1,
            textColor = SoptTheme.colors.white
        )
    }
}
