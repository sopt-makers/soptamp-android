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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.LoginNavGraph
import org.sopt.stamp.config.navigation.SignUpNavGraph
import org.sopt.stamp.designsystem.style.SoptTheme

@LoginNavGraph
@Destination("login")
@Composable
private fun LoginPage() {
    Column(
        modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Image(
            painter = painterResource(id = R.drawable.ic_soptamp),
            contentDescription = "sopatmp logo",
        )

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            LoginTextField(inputDesc = "이메일을 입력해주세요", input = username, fillMaxWidth = true)

            Spacer(modifier = Modifier.height(20.dp))
            LoginTextField(inputDesc = "비밀번호를 입력해주세요", input = password, fillMaxWidth = true)

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "계정찾기",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = { }),
                style = SoptTheme.typography.caption3,
                color = SoptTheme.colors.onSurface50
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
        ) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFC292FF), contentColor = Color(0xFFFFFFFF)
                )
            ) {
                Text(text = "로그인")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "회원가입",
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(onClick = { }),
            style = SoptTheme.typography.caption1
        )
    }
}

@Composable
private fun LoginTextField(inputDesc: String, input: MutableState<TextFieldValue>, fillMaxWidth: Boolean) {
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
        onValueChange = { input.value = it },
        placeholder = {
            Text(
                text = inputDesc, style = SoptTheme.typography.caption1
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    SoptTheme {
        LoginPage()
    }
}
