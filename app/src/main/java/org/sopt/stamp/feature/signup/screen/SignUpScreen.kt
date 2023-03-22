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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.SignUpNavGraph
import org.sopt.stamp.designsystem.component.button.SoptampButton
import org.sopt.stamp.designsystem.component.button.SoptampIconButton
import org.sopt.stamp.designsystem.component.dialog.NetworkErrorDialog
import org.sopt.stamp.designsystem.component.layout.LoadingScreen
import org.sopt.stamp.designsystem.component.topappbar.SoptTopAppBar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.fake.FakeUserRepository
import org.sopt.stamp.feature.destinations.LoginPageScreenDestination
import org.sopt.stamp.feature.destinations.SignUpCompleteScreenDestination
import org.sopt.stamp.feature.signup.SignUpViewModel
import org.sopt.stamp.feature.signup.component.RegisterInputField
import org.sopt.stamp.feature.signup.component.RegisterPasswordInputField
import org.sopt.stamp.feature.signup.model.RegisterState
import org.sopt.stamp.feature.signup.model.RegisterUiModel
import org.sopt.stamp.util.rememberKeyBoardState

@SignUpNavGraph(true)
@Destination("register")
@Composable
fun SignUpPageScreen(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<Boolean>,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    SoptTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            var uiModel by remember {
                mutableStateOf(RegisterState.Default(RegisterUiModel.empty).uiModel)
            }
            when (state) {
                is RegisterState.Default -> {
                    uiModel = (state as RegisterState.Default).uiModel
                }

                RegisterState.Loading -> {
                    LoadingScreen()
                }

                RegisterState.Failure -> {
                    NetworkErrorDialog()
                }
            }
            RegisterScreen(
                uiModel = uiModel,
                onNicknameChange = { viewModel.putNickname(it) },
                onEmailChange = { viewModel.putEmail(it) },
                onPasswordChange = { viewModel.putPassword(it) },
                onPasswordConfirmChange = { viewModel.putPasswordConfirm(it) },
                onClickCheckNickname = {},
                onClickCheckEmail = {},
                onClickRegister = {
                    navigator.navigate(
                        direction = SignUpCompleteScreenDestination,
                        builder = {
                            popUpTo(LoginPageScreenDestination) {
                                inclusive = true
                            }
                        }
                    )
                },
                onClickBackNav = { resultBackNavigator.navigateBack() }
            )
        }
    }
}


@Composable
fun RegisterScreen(
    uiModel: RegisterUiModel = RegisterUiModel.empty,
    onNicknameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onPasswordConfirmChange: (String) -> Unit = {},
    onClickCheckNickname: () -> Unit = {},
    onClickCheckEmail: () -> Unit = {},
    onClickRegister: () -> Unit = {},
    onClickBackNav: () -> Unit = {}
) {
    Scaffold(
        topBar = { RegisterHeader(onClickBackNav = { onClickBackNav() }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val isShowKeyboard by rememberKeyBoardState()
            var isFocusPassword by remember { mutableStateOf(false) }
            var isFocusPasswordConfirm by remember { mutableStateOf(false) }
            val inputNickname = remember {
                mutableStateOf(TextFieldValue(uiModel.nickname))
            }
            val inputEmail = remember {
                mutableStateOf(TextFieldValue(uiModel.email))
            }
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(
                visible = !((isFocusPassword or isFocusPasswordConfirm) and isShowKeyboard),
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                Column {
                    RegisterInputField(
                        title = "닉네임",
                        input = inputNickname,
                        onTextChange = { onNicknameChange(it) },
                        labelText = "한글/영문 NN자로 입력해주세요.",
                        isError = !(uiModel.nicknameCheckState.isPass),
                        message = uiModel.nicknameCheckMessage,
                        onClickButton = { onClickCheckNickname() }
                    )
                    Spacer(modifier = Modifier.size(38.dp))
                    RegisterInputField(
                        title = "이메일",
                        input = inputEmail,
                        onTextChange = { onEmailChange(it) },
                        labelText = "이메일을 입력해주세요.",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = !(uiModel.emailCheckState.isPass),
                        message = uiModel.emailCheckMessage,
                        onClickButton = { onClickCheckEmail() }
                    )
                    Spacer(modifier = Modifier.size(38.dp))
                }
            }
            RegisterPasswordInputField(
                title = "비밀번호",
                onPasswordChange = { onPasswordChange(it) },
                onPasswordConfirmChange = { onPasswordConfirmChange(it) },
                passwordLabelText = "영문, 숫자, 특수문자 포함 8자 이상 16자 이하 입력해주세요.",
                passwordConfirmLabelText = "확인을 위해 비밀번호를 한 번 더 입력해주세요.",
                isErrorPassword = !uiModel.passwordCheckState.isPass,
                isErrorPasswordConfirm = !uiModel.passwordConfirmCheckState.isPass,
                message = uiModel.passwordCheckMessage,
                onFocusPassword = { isFocusPassword = it.hasFocus },
                onFocusPasswordConfirm = { isFocusPasswordConfirm = it.hasFocus }
            )
            Spacer(modifier = Modifier.size(68.dp))
            SoptampButton(
                text = "가입하기",
                textStyle = SoptTheme.typography.h2,
                textColor = SoptTheme.colors.white,
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = SoptTheme.colors.purple300,
                    disabledBackgroundColor = SoptTheme.colors.purple200
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClickRegister() },
                isEnable = uiModel.isAllInputNotEmpty
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RegisterHeader(
    onClickBackNav: () -> Unit = {}
) {
    SoptTopAppBar(
        title = { RegisterHeaderTitle() },
        navigationIcon = {
            SoptampIconButton(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
                onClick = { onClickBackNav() }
            )
        },
        contentPadding = PaddingValues(start = 16.dp)
    )
}

@Composable
fun RegisterHeaderTitle() {
    Text(
        text = "회원가입",
        style = SoptTheme.typography.h2,
        color = SoptTheme.colors.black,
        fontSize = 20.sp
    )
}

@Preview(backgroundColor = 0xFFFFF, showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    SoptTheme {
        RegisterScreen()
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
