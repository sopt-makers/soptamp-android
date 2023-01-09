package org.sopt.stamp.feature.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.style.SoptTheme

// edittext id 이메일을 입력해주세요.
// edittext password 비밀번호를 입력해주세요.
// Button 계정 찾기
// Button 로그인
// Button 회원가입

@Composable
private fun LoginPage() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Image(
            painter = painterResource(id = R.drawable.ic_soptamp),
            contentDescription = "sopatmp logo",
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            shape = RoundedCornerShape(12.dp),
            label = { Text(text = "이메일을 입력해주세요.") },
            value = username.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF121212),
                backgroundColor = Color(0xFFF7F8F9),
                placeholderColor = Color(0xFF666768),
                disabledTextColor = Color(0xFF666768)
            ),
            onValueChange = { username.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            shape = RoundedCornerShape(12.dp),
            label = { Text(text = "비밀번호를 입력해주세요.") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF121212),
                backgroundColor = Color(0xFFF7F8F9),
                placeholderColor = Color(0xFF666768),
                disabledTextColor = Color(0xFF666768)
            ),
            onValueChange = { password.value = it }
        )

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
                    backgroundColor = Color(0xFFC292FF),
                    contentColor = Color(0xFFFFFFFF)
                )
            ) {
                Text(text = "로그인")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("회원가입"),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    SoptTheme {
        LoginPage()
    }
}
