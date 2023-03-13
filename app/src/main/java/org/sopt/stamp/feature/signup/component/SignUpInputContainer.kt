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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
fun SignUpInputContainer(
    inputTitle: String,
    inputDesc: String,
    input: MutableState<TextFieldValue>,
    keyboardType: KeyboardType,
    checkInput: () -> Unit,
    putInput: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = inputTitle
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SignUpTextField(inputDesc, input, keyboardType, false, putInput)
            Box {
                Button(
                    onClick = { checkInput.invoke() },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(start = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = SoptTheme.colors.purple300,
                        contentColor = SoptTheme.colors.white
                    )
                ) {
                    Text(
                        text = "확인",
                        style = SoptTheme.typography.sub3
                    )
                }
            }
        }
    }
}

@DefaultPreview
@Composable
private fun SignUpInputContainerReview() {
    SoptTheme {
        val password = remember { mutableStateOf(TextFieldValue()) }
        SignUpInputContainer(
            inputTitle = "",
            inputDesc = "",
            input = password,
            keyboardType = KeyboardType.Email,
            checkInput = { },
            putInput = { }
        )
    }
}
