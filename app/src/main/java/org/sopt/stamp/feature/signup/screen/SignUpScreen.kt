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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.sopt.stamp.config.navigation.SignUpNavGraph
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.component.toolbar.Toolbar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.fake.FakeUserRepository
import org.sopt.stamp.feature.signup.SignUpAction
import org.sopt.stamp.feature.signup.SignUpViewModel
import org.sopt.stamp.feature.signup.component.PasswordTextField
import org.sopt.stamp.feature.signup.component.SignUpInputContainer

@SignUpNavGraph(true)
@Destination("Page")
@Composable
fun SignUpPageScreen(
    navigator: DestinationsNavigator,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val isSubmitEnabled by viewModel.isSubmitEnabled.collectAsState(false)
    SoptTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        bottom = 10.dp
                    ),
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
                    .padding(padding)
                    .fillMaxSize()
                    .background(SoptTheme.colors.white)
            ) {
                val nickname = remember { mutableStateOf(TextFieldValue()) }
                val email = remember { mutableStateOf(TextFieldValue()) }
                val password = remember { mutableStateOf(TextFieldValue()) }
                Spacer(modifier = Modifier.height(20.dp))
                SignUpInputContainer(
                    "닉네임",
                    "닉네임을 입력해주세요",
                    nickname,
                    checkInput = { viewModel.handleAction(SignUpAction.CheckNickname) },
                    keyboardType = KeyboardType.Text,
                    putInput = { input -> viewModel.handleAction(SignUpAction.PutNickname(input)) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                SignUpInputContainer(
                    "이메일",
                    "이메일을 입력해주세요",
                    email,
                    checkInput = { viewModel.handleAction(SignUpAction.CheckEmail) },
                    keyboardType = KeyboardType.Email,
                    putInput = { input -> viewModel.handleAction(SignUpAction.PutEmail(input)) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                PasswordTextField(
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
                        backgroundColor = SoptTheme.colors.purple300,
                        contentColor = SoptTheme.colors.white,
                        disabledBackgroundColor = SoptTheme.colors.purple200,
                        disabledContentColor = SoptTheme.colors.white
                    ),
                    enabled = isSubmitEnabled
                ) {
                    Text(
                        text = "가입하기",
                        style = SoptTheme.typography.h2
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SoptTheme {
        SignUpPageScreen(
            viewModel = SignUpViewModel(FakeUserRepository),
            navigator = EmptyDestinationsNavigator
        )
    }
}
