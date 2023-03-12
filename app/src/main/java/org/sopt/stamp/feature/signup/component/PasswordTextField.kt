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
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

@Composable
fun PasswordTextField(
    inputTitle: String,
    firstInputDesc: String,
    secondInputDesc: String,
    password: MutableState<TextFieldValue>,
    checkInputSame: () -> Unit,
    keyboardType: KeyboardType,
    putPassword: (String) -> Unit,
    putPasswordConfirm: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = inputTitle)
        Spacer(modifier = Modifier.height(16.dp))
        SignUpTextField(firstInputDesc, password, keyboardType, true, putPassword)
        Spacer(modifier = Modifier.height(12.dp))
        SignUpTextField(secondInputDesc, password, keyboardType, true, putPasswordConfirm)
    }
}

@DefaultPreview
@Composable
private fun PasswordTextFieldPreview() {
    SoptTheme {
        val password = remember { mutableStateOf(TextFieldValue()) }
        PasswordTextField(
            "비밀번호",
            "비밀번호를 입력해주세요.",
            "비밀번호를 다시 입력해주세요.",
            password,
            checkInputSame = { },
            keyboardType = KeyboardType.Password,
            putPassword = { },
            putPasswordConfirm = { }
        )
    }
}
