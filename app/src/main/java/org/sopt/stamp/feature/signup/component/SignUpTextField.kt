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
package org.sopt.stamp.feature.signup.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.component.textfield.TextField
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

@Composable
fun SignUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    keyboardType: KeyboardType,
    fillMaxWidth: Boolean
) {
    val colors = SoptTheme.colors
    val isEmpty = remember(value) {
        value.isEmpty()
    }
    val modifier = remember(fillMaxWidth, isEmpty) {
        val baseModifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = if (isEmpty) 0.dp else 1.dp,
                color = colors.purple300,
                shape = RoundedCornerShape(10.dp)
            )
        if (fillMaxWidth) {
            baseModifier.fillMaxWidth()
        } else {
            baseModifier
        }
    }
    val visualTransformation = remember(keyboardType) {
        if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    }

    TextField(
        value = value,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isEmpty) SoptTheme.colors.onSurface5 else Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = SoptTheme.colors.onSurface90,
            placeholderColor = SoptTheme.colors.onSurface60
        ),
        visualTransformation = visualTransformation,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeHolder,
                style = SoptTheme.typography.caption1,
                textAlign = TextAlign.Center
            )
        },
        textStyle = SoptTheme.typography.caption1,
        singleLine = true
    )
}

@DefaultPreview
@Composable
private fun SignUpTextFieldPreview() {
    SoptTheme {
        var nickname by remember { mutableStateOf("") }
        SignUpTextField(
            placeHolder = "닉네임",
            value = nickname,
            keyboardType = KeyboardType.Text,
            fillMaxWidth = true,
            onValueChange = { nickname = it }
        )
    }
}
