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
package org.sopt.stamp.designsystem.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.stamp.designsystem.style.SoptTheme

enum class TextFieldType {
    GRAY,
    WHITE,
    ERROR;

    val backgroundColor: Color
        @Composable get() = when (this) {
            GRAY -> SoptTheme.colors.onSurface5
            WHITE -> SoptTheme.colors.white
            ERROR -> SoptTheme.colors.error100
        }

    val borderColor: Color
        @Composable get() = when (this) {
            GRAY -> SoptTheme.colors.onSurface5
            WHITE -> SoptTheme.colors.purple300
            ERROR -> SoptTheme.colors.error200
        }
}

@Composable
fun SoptampTextField(
    modifier: Modifier = Modifier,
    input: MutableState<TextFieldValue>,
    onTextChange: (String) -> Unit = {},
    enabled: Boolean = true,
    readOnly: Boolean = false,
    labelText: String = "",
    placeholderText: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onFocus: (FocusState) -> Unit = {}
) {
    var textFieldType by remember {
        mutableStateOf(TextFieldType.GRAY)
    }

    val isInputTextIsEmpty = input.value.text.isEmpty()

    val inputTextFieldShape = RoundedCornerShape(12.dp)

    val inputTextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = textFieldType.backgroundColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorLabelColor = SoptTheme.colors.error200,
        errorIndicatorColor = Color.Transparent,
        textColor = if (isInputTextIsEmpty) SoptTheme.colors.onSurface60 else SoptTheme.colors.black,
        placeholderColor = SoptTheme.colors.black,
        focusedLabelColor = SoptTheme.colors.onSurface40,
        unfocusedLabelColor = SoptTheme.colors.onSurface40
    )

    val inputTextFieldModifier = modifier.border(
        width = if (isInputTextIsEmpty) 0.dp else 1.dp,
        color = textFieldType.borderColor,
        shape = inputTextFieldShape
    ).onFocusEvent {
        textFieldType = if (isError) {
            TextFieldType.ERROR
        } else {
            if (it.hasFocus) {
                TextFieldType.WHITE
            } else {
                TextFieldType.GRAY
            }
        }
        onFocus(it)
    }

    TextField(
        value = input.value.text,
        onValueChange = {
            input.value = TextFieldValue(it)
            onTextChange(it)
        },
        modifier = inputTextFieldModifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = SoptTheme.typography.caption1,
        label = {
            Text(
                text = labelText,
                style = SoptTheme.typography.caption4
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = SoptTheme.typography.caption1
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = inputTextFieldShape,
        colors = inputTextFieldColors
    )
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PreviewSoptampTextField() {
    SoptTheme {
        val input = remember {
            mutableStateOf(TextFieldValue())
        }
        Column {
            SoptampTextField(
                input = input,
                onTextChange = {},
                placeholderText = "이메일을 입력해주세요.",
                labelText = "ID"
            )
        }
    }
}
