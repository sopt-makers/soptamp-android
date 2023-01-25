package org.sopt.stamp.feature.signup.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.sopt.stamp.config.navigation.SignUpNavGraph
import org.sopt.stamp.designsystem.component.topappbar.SoptTopAppBar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.signup.SignUpAction
import org.sopt.stamp.feature.signup.SoptampSignUpViewModel

@SignUpNavGraph(true)
@Destination("Page")
@Composable
fun SignUpPageScreen(
    viewModel: SoptampSignUpViewModel = hiltViewModel()
) {
    SoptTheme {
        Scaffold(topBar = { SoptTopAppBar(title = { Text(text = "회원가입") }) }) { paddingValues ->
            val defaultPadding = PaddingValues(
                top = 0.dp, bottom = paddingValues.calculateBottomPadding(), start = 16.dp, end = 16.dp
            )
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
                    putInput = { input -> viewModel.handleAction(SignUpAction.PutNickname(input)) }
                )

                Spacer(modifier = Modifier.height(20.dp))
                SignUpInput(
                    "이메일",
                    "이메일을 입력해주세요",
                    email,
                    checkInput = { viewModel.handleAction(SignUpAction.CheckEmail) },
                    putInput = { input -> viewModel.handleAction(SignUpAction.PutEmail(input)) }
                )

                Spacer(modifier = Modifier.height(20.dp))
                PasswordInput(
                    "비밀번호",
                    "비밀번호를 입력해주세요.",
                    "비밀번호를 다시 입력해주세요.",
                    password,
                    checkInputSame = { viewModel.handleAction(SignUpAction.CheckPassword) },
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
                        backgroundColor = Color(0xFFC292FF), contentColor = Color(0xFFFFFFFF)
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
    checkInput: () -> Unit,
    putInput: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = inputTitle, modifier = Modifier.padding(bottom = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            SignUpTextField(inputDesc, input, false, putInput)
            Box {
                Button(
                    onClick = { checkInput.invoke() },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC292FF), contentColor = Color(0xFFFFFFFF)
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
    putPassword: (String) -> Unit,
    putPasswordConfirm: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = inputTitle)
        Spacer(modifier = Modifier.height(16.dp))
        SignUpTextField(firstInputDesc, password, true, putPassword)
        Spacer(modifier = Modifier.height(12.dp))
        SignUpTextField(secondInputDesc, password, true, putPasswordConfirm)
    }
}

@Composable
private fun SignUpTextField(inputDesc: String, input: MutableState<TextFieldValue>, fillMaxWidth: Boolean, putInput: (String) -> Unit) {
    var modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .border(
            width = if (input.value.text.isEmpty()) 0.dp else 1.dp, color = Color(0xFFC292FF), shape = RoundedCornerShape(10.dp)
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
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
            input.value = it
            putInput(input.value.text)
        },
        placeholder = {
            Text(
                text = inputDesc, style = SoptTheme.typography.caption1
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SoptTheme {
        SignUpPageScreen()
    }
}
