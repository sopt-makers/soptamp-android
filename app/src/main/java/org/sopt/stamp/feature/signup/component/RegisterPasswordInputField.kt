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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.component.textfield.SoptampTextField
import org.sopt.stamp.designsystem.style.SoptTheme

@Composable
fun RegisterPasswordInputField(
    title: String,
    onPasswordChange: (String) -> Unit = {},
    onPasswordConfirmChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    visualTransformation: PasswordVisualTransformation = PasswordVisualTransformation(),
    passwordLabelText: String = "",
    passwordConfirmLabelText: String = "",
    isErrorPassword: Boolean = false,
    isErrorPasswordConfirm: Boolean = false,
    message: String = "",
    onFocusPassword: (FocusState) -> Unit = {},
    onFocusPasswordConfirm: (FocusState) -> Unit = {}
) {
    val passwordInput = remember {
        mutableStateOf(TextFieldValue())
    }

    val passwordConfirmInput = remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Text(
            text = title,
            style = SoptTheme.typography.sub1,
            color = SoptTheme.colors.onSurface90
        )
        Spacer(modifier = Modifier.size(16.dp))
        SoptampTextField(
            input = passwordInput,
            onTextChange = { onPasswordChange(it) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            labelText = passwordLabelText,
            isError = isErrorPassword,
            onFocus = { onFocusPassword(it) }
        )
        Spacer(modifier = Modifier.size(12.dp))
        SoptampTextField(
            input = passwordConfirmInput,
            onTextChange = { onPasswordConfirmChange(it) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            labelText = passwordConfirmLabelText,
            isError = isErrorPasswordConfirm,
            onFocus = { onFocusPasswordConfirm(it) }
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = message,
            style = SoptTheme.typography.caption3,
            color = if (isErrorPassword or isErrorPasswordConfirm) {
                SoptTheme.colors.error300
            } else {
                SoptTheme.colors.access300
            }
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PreviewRegisterPasswordInputField() {
    SoptTheme {
        RegisterPasswordInputField(title = "비밀번호")
    }
}
