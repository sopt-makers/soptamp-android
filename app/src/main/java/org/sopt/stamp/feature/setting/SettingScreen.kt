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
package org.sopt.stamp.feature.setting

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jakewharton.processphoenix.ProcessPhoenix
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.SettingNavGraph
import org.sopt.stamp.designsystem.component.dialog.DoubleOptionDialog
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.component.toolbar.Toolbar
import org.sopt.stamp.designsystem.style.Access300
import org.sopt.stamp.designsystem.style.Gray50
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.fake.FakeStampRepository
import org.sopt.stamp.domain.fake.FakeUserRepository
import org.sopt.stamp.domain.usecase.GetUserIdUseCase
import org.sopt.stamp.feature.setting.component.Section
import org.sopt.stamp.feature.setting.model.SectionUiModel
import org.sopt.stamp.util.DefaultPreview

@SettingNavGraph(true)
@Destination("menu")
@Composable
fun SettingScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingScreenViewModel = hiltViewModel()
) {
    val myInfoSectionItems = remember {
        listOf(
            SectionUiModel.Header(title = "내 정보"),
            SectionUiModel.Option(
                title = "한 마디 편집",
                optionIconResId = R.drawable.arrow_right
            ) {
                // TODO by Nunu 한 마디 편집 화면으로 넘어가기
            },
            SectionUiModel.Spacer,
            SectionUiModel.Option(
                title = "비밀번호 변경",
                optionIconResId = R.drawable.arrow_right
            ) {
                // TODO by Nunu 비밀번호 변경 화면으로 넘어가기
            },
            SectionUiModel.Spacer,
            SectionUiModel.Option(
                title = "닉네임 변경",
                optionIconResId = R.drawable.arrow_right,
                containerModifier = Modifier
                    .background(
                        color = Gray50,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
                    .padding(horizontal = 16.dp)
            ) {
                // TODO by Nunu 닉네임 변경 화면으로 넘어가기
            }
        )
    }

    val serviceTermSectionItems = remember {
        listOf(
            SectionUiModel.Header(title = "서비스 이용방침"),
            SectionUiModel.Option(
                title = "개인정보처리방침",
                optionIconResId = R.drawable.arrow_right
            ) {
                // TODO by Nunu 개인정보처리방침 화면으로 넘어가기
            },
            SectionUiModel.Spacer,
            SectionUiModel.Option(
                title = "서비스 이용 약관",
                optionIconResId = R.drawable.arrow_right
            ) {
                // TODO by Nunu 서비스 이용 약관 화면으로 넘어가기
            },
            SectionUiModel.Spacer,
            SectionUiModel.Option(
                title = "서비스 의견 제안",
                optionIconResId = R.drawable.arrow_right,
                containerModifier = Modifier
                    .background(
                        color = Gray50,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
                    .padding(horizontal = 16.dp)
            ) {
                // TODO by Nunu 서비스 의견 제안 화면으로 넘어가기
            }
        )
    }

    val missionSectionItems = listOf(
        SectionUiModel.Header(title = "미션"),
        SectionUiModel.Option(
            title = "스탬프 초기화",
            containerModifier = Modifier
                .background(
                    color = Gray50,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            viewModel.onClearAllStamps()
        }
    )

    val logOutSectionItems = listOf(
        SectionUiModel.Option(
            title = "로그아웃",
            textColor = Access300,
            containerModifier = Modifier
                .background(
                    color = Gray50,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            viewModel.onLogout()
        }
    )

    SoptTheme {
        SoptColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            val uiState by viewModel.uiState.collectAsState(null)
            val context = LocalContext.current

            LaunchedEffect(uiState) {
                when (uiState) {
                    is SettingUiState.Success -> {
                        val success: SettingUiState.Success = uiState as SettingUiState.Success
                        when (success.action) {
                            SettingScreenAction.LOGOUT -> {
                                ProcessPhoenix.triggerRebirth(context)
                            }

                            SettingScreenAction.CLEAR_ALL_STAMP -> {}
                        }
                    }

                    is SettingUiState.Failure -> {
                        Toast.makeText(
                            context,
                            "요청에 실패했습니다. 재시도해주세요",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }

            Toolbar(
                modifier = Modifier.padding(bottom = 10.dp),
                title = {
                    Text(
                        text = "설정",
                        style = SoptTheme.typography.h2,
                        modifier = Modifier.padding(start = 4.dp),
                        color = SoptTheme.colors.onSurface
                    )
                },
                onBack = { navigator.popBackStack() }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Section(items = myInfoSectionItems)
            Spacer(modifier = Modifier.height(16.dp))
            Section(items = serviceTermSectionItems)
            Spacer(modifier = Modifier.height(16.dp))
            Section(items = missionSectionItems)
            Spacer(modifier = Modifier.height(16.dp))
            Section(items = logOutSectionItems)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "탈퇴하기",
                    style = SoptTheme.typography.caption1,
                    color = SoptTheme.colors.onSurface40,
                    textDecoration = TextDecoration.Underline
                )
            }
            if (uiState is SettingUiState.Dialog) {
                AlertDialog(
                    action = (uiState as SettingUiState.Dialog).action,
                    onLogout = { viewModel.onLogout() },
                    onClearStamps = { viewModel.onClearAllStamps() },
                    onDismiss = { viewModel.onDismiss() }
                )
            }
        }
    }
}

@Composable
private fun AlertDialog(
    action: SettingScreenAction,
    onLogout: () -> Unit,
    onClearStamps: () -> Unit,
    onDismiss: () -> Unit
) {
    val dialogParam = remember(action) {
        when (action) {
            SettingScreenAction.LOGOUT -> {
                AlertDialogModel(
                    "미션을 초기화 하시겠습니까?",
                    "사진, 메모가 삭제되고\n전체 미션이 미완료상태로 초기화됩니다.",
                    { onDismiss() },
                    { onLogout() }
                )
            }

            SettingScreenAction.CLEAR_ALL_STAMP -> {
                AlertDialogModel(
                    title = "달성한 미션을 삭제하시겠습니까?",
                    onCancel = { onDismiss() },
                    onConfirm = { onClearStamps() }
                )
            }
        }
    }
    val (title, subTitle, onCancel, onConfirm) = dialogParam
    DoubleOptionDialog(
        title = title,
        subTitle = subTitle,
        onConfirm = onConfirm,
        onCancel = onCancel
    )
}

private data class AlertDialogModel(
    val title: String,
    val subTitle: String = "",
    val onCancel: () -> Unit,
    val onConfirm: () -> Unit
)

@DefaultPreview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        navigator = EmptyDestinationsNavigator,
        viewModel = SettingScreenViewModel(
            FakeUserRepository,
            FakeStampRepository,
            GetUserIdUseCase(FakeUserRepository)
        )
    )
}
