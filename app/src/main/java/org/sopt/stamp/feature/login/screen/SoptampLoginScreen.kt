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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.LoginNavGraph
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.destinations.MissionListScreenDestination
import org.sopt.stamp.feature.destinations.SignUpPageScreenDestination
import org.sopt.stamp.feature.login.LoginAction
import org.sopt.stamp.feature.login.SoptampLoginViewModel

@LoginNavGraph(true)
@Destination("page")
@Composable
fun LoginPageScreen(
    viewModel: SoptampLoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val viewState = viewModel.viewState.collectAsState()
    if (viewState.value.isComplete) {
        navigator.navigate(MissionListScreenDestination())
    }
    SoptTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val username = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }

            Image(
                painter = painterResource(id = R.drawable.ic_soptamp),
                contentDescription = "soptamp logo",
                modifier = Modifier.padding(vertical = 72.dp)
            )

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
                    text = "계정찾기",
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(onClick = { }),
                    style = SoptTheme.typography.caption3,
                    color = SoptTheme.colors.onSurface50
                )
            }

            Spacer(modifier = Modifier.height(56.dp))
            Box {
                Button(
                    onClick = { viewModel.handleAction(LoginAction.Login) },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC292FF),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Text(text = "로그인")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "회원가입",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = { navigator.navigate(SignUpPageScreenDestination) }),
                style = SoptTheme.typography.caption1
            )
        }
    }
}

@Composable
private fun LoginTextField(
    inputDesc: String,
    keyboardType: KeyboardType,
    input: MutableState<TextFieldValue>,
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
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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
fun PreviewLoginScreen() {
    SoptTheme {
        // LoginPageScreen()
    }
}
