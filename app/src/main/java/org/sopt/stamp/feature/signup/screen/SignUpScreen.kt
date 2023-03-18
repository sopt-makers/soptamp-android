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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import org.sopt.stamp.config.navigation.SignUpNavGraph
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.component.toolbar.Toolbar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.fake.FakeUserRepository
import org.sopt.stamp.feature.signup.SignUpViewModel

@SignUpNavGraph(true)
@Destination("Page")
@Composable
fun SignUpPageScreen(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<Boolean>,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        resultBackNavigator.navigateBack(true)
    }

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
                Spacer(modifier = Modifier.height(20.dp))
                // TODO: 닉네임 입력 컴포넌트
                Spacer(modifier = Modifier.height(20.dp))
                // TODO: 이메일 입력 컴포넌트
                Spacer(modifier = Modifier.height(20.dp))
                // TODO: 비밀번호 입력 컴포넌트
                Spacer(modifier = Modifier.height(90.dp))
                Button(
                    onClick = {
                        // TODO: 가입하기 버튼 클릭 시,
                    },
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
                    enabled = false
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
            resultBackNavigator = EmptyResultBackNavigator(),
            navigator = EmptyDestinationsNavigator
        )
    }
}
