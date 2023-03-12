/*
 * Copyright 2022-2023 SOPT - Shout Our Passion Together
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
package org.sopt.stamp.feature.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.LoginNavGraph
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.fake.FakeUserRepository
import org.sopt.stamp.domain.usecase.auth.AutoLoginUseCase
import org.sopt.stamp.domain.usecase.auth.GetUserIdUseCase
import org.sopt.stamp.feature.destinations.MissionListScreenDestination
import org.sopt.stamp.feature.destinations.SignUpPageScreenDestination
import org.sopt.stamp.feature.login.LoginAction
import org.sopt.stamp.feature.login.LoginViewModel
import org.sopt.stamp.feature.login.component.LoginTextField

@LoginNavGraph(true)
@Destination("page")
@Composable
fun LoginPageScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState) {
        if (uiState.isComplete) {
            navigator.navigate(MissionListScreenDestination())
        }
    }
    LaunchedEffect(Unit) {
        viewModel.onAutoLogin()
    }

    if (!uiState.isComplete) {
        SoptTheme {
            SoptColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SoptTheme.colors.white),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val username = remember { mutableStateOf(TextFieldValue()) }
                val password = remember { mutableStateOf(TextFieldValue()) }

                Image(
                    painter = painterResource(id = R.drawable.ic_soptamp),
                    contentDescription = "soptamp logo",
                )
                Spacer(modifier = Modifier.height(72.dp))
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    LoginTextField(
                        inputDesc = "이메일을 입력해주세요",
                        input = username,
                        fillMaxWidth = true,
                        keyboardType = KeyboardType.Email,
                        putInput = { input ->
                            viewModel.handleAction(LoginAction.PutEmail(input))
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LoginTextField(
                        inputDesc = "비밀번호를 입력해주세요",
                        input = password,
                        fillMaxWidth = true,
                        keyboardType = KeyboardType.Password,
                        putInput = { input ->
                            viewModel.handleAction(LoginAction.PutPassword(input))
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "계정 찾기",
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { },
                        style = SoptTheme.typography.caption2,
                        color = SoptTheme.colors.onSurface50
                    )
                }

                Spacer(modifier = Modifier.height(56.dp))
                Box {
                    Button(
                        onClick = { viewModel.handleAction(LoginAction.Login) },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = SoptTheme.colors.purple300,
                            contentColor = SoptTheme.colors.white
                        )
                    ) {
                        Text(
                            text = "로그인",
                            style = SoptTheme.typography.h2,
                            color = SoptTheme.colors.white
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "회원가입",
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable { navigator.navigate(SignUpPageScreenDestination) },
                    style = SoptTheme.typography.caption1,
                    color = SoptTheme.colors.onSurface90
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    SoptTheme {
        LoginPageScreen(
            viewModel = LoginViewModel(
                FakeUserRepository,
                AutoLoginUseCase(GetUserIdUseCase(FakeUserRepository))
            ),
            navigator = EmptyDestinationsNavigator
        )
    }
}
