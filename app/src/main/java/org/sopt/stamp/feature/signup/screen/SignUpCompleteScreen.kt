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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.SignUpNavGraph
import org.sopt.stamp.designsystem.component.button.SoptampButton
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.destinations.LoginPageScreenDestination

@SignUpNavGraph
@Destination("complete")
@Composable
fun SignUpCompleteScreen(
    navigator: DestinationsNavigator
) {
    SoptTheme() {
        SignUpCompleteScreen(
            onClickRegisterComplete = {
                navigator.navigate(
                    direction = LoginPageScreenDestination,
                    builder = {
                        launchSingleTop = true
                    }
                )
            }
        )
    }
}

@Composable
fun SignUpCompleteScreen(
    onClickRegisterComplete: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.register_complete),
            contentDescription = "Register Complete Screen Image"
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "가입완료",
            style = SoptTheme.typography.h1,
            fontSize = 20.sp,
            color = SoptTheme.colors.onSurface90
        )
        Text(
            text = "SOPTAMP에 오신 것을 환영합니다",
            style = SoptTheme.typography.sub2,
            fontSize = 20.sp,
            color = SoptTheme.colors.onSurface50
        )
        Spacer(modifier = Modifier.weight(1f))
        SoptampButton(
            text = "가입하기",
            textStyle = SoptTheme.typography.h2,
            textColor = SoptTheme.colors.white,
            buttonColors = ButtonDefaults.buttonColors(
                backgroundColor = SoptTheme.colors.purple300,
                disabledBackgroundColor = SoptTheme.colors.purple200
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClickRegisterComplete() }
        )
        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun PreviewRegisterCompleteScreen() {
    SoptTheme {
        SignUpCompleteScreen()
    }
}
