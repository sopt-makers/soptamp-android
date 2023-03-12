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
package org.sopt.stamp.feature.signup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.sopt.stamp.config.navigation.SignUpNavGraph
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.component.toolbar.Toolbar
import org.sopt.stamp.designsystem.component.topappbar.SoptTopAppBar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.fake.FakeUserRepository
import org.sopt.stamp.feature.signup.SignUpAction
import org.sopt.stamp.feature.signup.SoptampSignUpViewModel

@SignUpNavGraph(true)
@Destination("Page")
@Composable
fun SignUpPageScreen(
    navigator: DestinationsNavigator,
    viewModel: SoptampSignUpViewModel = hiltViewModel()
) {
    SoptTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    modifier = Modifier.padding(bottom = 10.dp),
                    title = {
                        Text(
                            text = "닉네임 변경",
                            style = SoptTheme.typography.h2,
                            modifier = Modifier.padding(start = 4.dp),
                            color = SoptTheme.colors.onSurface
                        )
                    },
                    onBack = { navigator.popBackStack() }
                )
            }
        ) { padding ->
            SoptColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SoptTheme.colors.white),
            ) {
                val nickname = remember { mutableStateOf(TextFieldValue()) }
                val email = remember { mutableStateOf(TextFieldValue()) }
                val password = remember { mutableStateOf(TextFieldValue()) }

                Spacer(modifier = Modifier.height(20.dp))
                SignUpInput(
                    "닉네임",
                    "닉네임을 입력해주세요",
                    nickname,
                    checkInput = { viewModel.handleAction(SignUpAction.CheckNickname) },
                    keyboardType = KeyboardType.Text,
                    putInput = { input -> viewModel.handleAction(SignUpAction.PutNickname(input)) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                SignUpInput(
                    "이메일",
                    "이메일을 입력해주세요",
                    email,
                    checkInput = { viewModel.handleAction(SignUpAction.CheckEmail) },
                    keyboardType = KeyboardType.Email,
                    putInput = { input -> viewModel.handleAction(SignUpAction.PutEmail(input)) }
                )

                Spacer(modifier = Modifier.height(20.dp))
                PasswordInput(
                    "비밀번호",
                    "비밀번호를 입력해주세요.",
                    "비밀번호를 다시 입력해주세요.",
                    password,
                    checkInputSame = { viewModel.handleAction(SignUpAction.CheckPassword) },
                    keyboardType = KeyboardType.Password,
                    putPassword = { input -> viewModel.handleAction(SignUpAction.PutPassword(input)) },
                    putPasswordConfirm = { input -> viewModel.handleAction(SignUpAction.PutPasswordConfirm(input)) }
                )

                Spacer(modifier = Modifier.height(90.dp))
                Button(
                    onClick = { viewModel.handleAction(SignUpAction.SignUp) },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC292FF),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Text(text = "가입하기")
                }
            }
        }
    }
}

@Composable
private fun SignUpInput(
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
        Text(text = inputTitle, modifier = Modifier.padding(bottom = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SignUpTextField(inputDesc, input, keyboardType, false, putInput)
            Box {
                Button(
                    onClick = { checkInput.invoke() },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC292FF),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Text(text = "확인")
                }
            }
        }
    }
}

@Composable
private fun PasswordInput(
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

@Composable
private fun SignUpTextField(
    inputDesc: String,
    input: MutableState<TextFieldValue>,
    keyboardType: KeyboardType,
    fillMaxWidth: Boolean,
    putInput: (String) -> Unit
) {
    var modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .border(
            width = if (input.value.text.isEmpty()) 0.dp else 1.dp,
            color = Color(0xFFC292FF),
            shape = RoundedCornerShape(10.dp)
        )
    modifier = if (fillMaxWidth) modifier.fillMaxWidth() else modifier

    TextField(
        value = input.value,
        label = { Text(text = inputDesc) },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (input.value.text.isEmpty()) SoptTheme.colors.onSurface5 else Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = SoptTheme.colors.onSurface90,
            placeholderColor = SoptTheme.colors.onSurface60
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        onValueChange = {
            input.value = it
            putInput(input.value.text)
        },
        placeholder = {
            Text(
                text = inputDesc,
                style = SoptTheme.typography.caption1
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SoptTheme {
        SignUpPageScreen(
            viewModel = SoptampSignUpViewModel(FakeUserRepository),
            navigator = EmptyDestinationsNavigator
        )
    }
}
