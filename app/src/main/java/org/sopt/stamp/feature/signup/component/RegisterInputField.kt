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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.component.button.SoptampButton
import org.sopt.stamp.designsystem.component.textfield.SoptampTextField
import org.sopt.stamp.designsystem.style.SoptTheme

@Composable
fun RegisterInputField(
    title: String,
    input: MutableState<TextFieldValue>,
    onTextChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    labelText: String = "",
    placeholderText: String = "",
    isError: Boolean = false,
    message: String = "",
    buttonText: String = "확인",
    onClickButton: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Text(
            text = title,
            style = SoptTheme.typography.sub1,
            color = SoptTheme.colors.onSurface90
        )
        Spacer(modifier = Modifier.size(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SoptampTextField(
                input = input,
                onTextChange = { onTextChange(it) },
                modifier = Modifier.weight(0.77f),
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation,
                labelText = labelText,
                placeholderText = placeholderText,
                isError = isError
            )
            Spacer(modifier = Modifier.size(4.dp))
            SoptampButton(
                text = buttonText,
                textStyle = SoptTheme.typography.sub3,
                textColor = SoptTheme.colors.white,
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = SoptTheme.colors.purple300,
                    disabledBackgroundColor = SoptTheme.colors.purple200
                ),
                isEnable = !isError && input.value.text.isNotEmpty(),
                onClick = { onClickButton() }
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = message,
            style = SoptTheme.typography.caption3,
            color = if (isError) SoptTheme.colors.error300 else SoptTheme.colors.access300
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PreviewRegisterInputField() {
    val input = remember {
        mutableStateOf(TextFieldValue())
    }
    SoptTheme {
        RegisterInputField(
            title = "닉네임",
            input = input,
            labelText = "input content",
            message = "message"
        )
    }
}
