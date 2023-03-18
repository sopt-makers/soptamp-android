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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

@Composable
fun SignUpInputContainer(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    placeHolder: String,
    keyboardType: KeyboardType,
    onCheck: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = SoptTheme.typography.sub1,
            color = SoptTheme.colors.onSurface90,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.Top
        ) {
            SignUpTextField(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = onValueChange,
                placeHolder = placeHolder,
                keyboardType = keyboardType,
                fillMaxWidth = false,
            )
            Button(
                onClick = { onCheck() },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .padding(start = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SoptTheme.colors.purple300,
                    contentColor = SoptTheme.colors.white,
                    disabledBackgroundColor = SoptTheme.colors.purple200,
                    disabledContentColor = SoptTheme.colors.white
                ),
                contentPadding = PaddingValues(0.dp),
                enabled = value.isNotBlank()
            ) {
                Text(
                    text = "확인",
                    style = SoptTheme.typography.sub3
                )
            }
        }
    }
}

@DefaultPreview
@Composable
private fun SignUpInputContainerReview() {
    SoptTheme {
        var password by remember { mutableStateOf("") }
        SignUpInputContainer(
            title = "닉네임",
            placeHolder = "한글/영문 10자 이하로 입력해주세요",
            value = password,
            keyboardType = KeyboardType.Email,
            onCheck = { },
            onValueChange = { password = it }
        )
    }
}
