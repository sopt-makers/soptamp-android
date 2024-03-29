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
package org.sopt.stamp.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.stamp.domain.repository.UserRepository
import org.sopt.stamp.feature.signup.model.CheckState
import org.sopt.stamp.feature.signup.model.RegisterState
import org.sopt.stamp.feature.signup.model.RegisterUiModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Default(RegisterUiModel.empty))
    val state = _state.asStateFlow()

    fun putNickname(nickname: String) {
        _state.update { prevState ->
            check(prevState is RegisterState.Default)
            prevState.copy(
                uiModel = prevState.uiModel.updateNickname(nickname)
            )
        }
    }

    fun putEmail(email: String) {
        _state.update { prevState ->
            check(prevState is RegisterState.Default)
            prevState.copy(
                uiModel = prevState.uiModel.updateEmail(email)
            )
        }
    }

    fun putPassword(password: String) {
        _state.update { prevState ->
            check(prevState is RegisterState.Default)
            prevState.copy(
                uiModel = prevState.uiModel.updatePassword(password)
            )
        }
    }

    fun putPasswordConfirm(passwordConfirm: String) {
        _state.update { prevState ->
            check(prevState is RegisterState.Default)
            prevState.copy(
                uiModel = prevState.uiModel.updatePasswordConfirm(passwordConfirm)
            )
        }
    }

    fun checkNickName(nickname: String) {
        viewModelScope.launch {
            runCatching {
                userRepository.checkNickname(nickname)
            }.onSuccess {
                updateNickNamePassState()
            }.onFailure {
                _state.update { prevState ->
                    check(prevState is RegisterState.Default)
                    prevState.copy(
                        uiModel = prevState.uiModel.copy(
                            nicknameCheckMessage = "이미 사용중인 닉네임입니다.",
                            nicknameCheckState = CheckState.NONE_PASS,
                            isCheckNickname = false,
                            isRegisterEnable = prevState.uiModel.onCheckRegisterEnabled()
                        )
                    )
                }
                _state.update {
                    check(it is RegisterState.Default)
                    it.copy(
                        uiModel = it.uiModel.copy(
                            isRegisterEnable = it.uiModel.onCheckRegisterEnabled()
                        )
                    )
                }
            }
        }
    }

    fun checkEmail(email: String) {
        viewModelScope.launch {
            runCatching {
                userRepository.checkEmail(email)
            }.onSuccess {
                updateEmailPassState()
            }.onFailure {
                _state.update { prevState ->
                    check(prevState is RegisterState.Default)
                    prevState.copy(
                        uiModel = prevState.uiModel.copy(
                            emailCheckMessage = "이미 등록된 이메일입니다.",
                            emailCheckState = CheckState.NONE_PASS,
                            isCheckEmail = false,
                            isRegisterEnable = prevState.uiModel.onCheckRegisterEnabled()
                        )
                    )
                }
                _state.update {
                    check(it is RegisterState.Default)
                    it.copy(
                        uiModel = it.uiModel.copy(
                            isRegisterEnable = it.uiModel.onCheckRegisterEnabled()
                        )
                    )
                }
            }
        }
    }

    private fun updateNickNamePassState() {
        _state.update { prevState ->
            check(prevState is RegisterState.Default)
            prevState.copy(
                uiModel = prevState.uiModel.copy(
                    nicknameCheckMessage = "사용 가능한 이름입니다.",
                    nicknameCheckState = CheckState.PASS,
                    isCheckNickname = true,
                    isRegisterEnable = prevState.uiModel.onCheckRegisterEnabled()
                )
            )
        }
        _state.update {
            check(it is RegisterState.Default)
            it.copy(
                uiModel = it.uiModel.copy(
                    isRegisterEnable = it.uiModel.onCheckRegisterEnabled()
                )
            )
        }
    }

    fun onSubmit() {
        val currentState = _state.value as RegisterState.Default
        val nickname = currentState.uiModel.nickname
        val email = currentState.uiModel.email
        val password = currentState.uiModel.password
        _state.value = RegisterState.Loading
        viewModelScope.launch {
            runCatching {
                userRepository.signup(nickname, email, password, "android", "null")
            }.onSuccess {
                _state.value = RegisterState.Success
            }.onFailure {
                _state.value = RegisterState.Failure(currentState.uiModel)
            }
        }
    }

    fun onRetry(uiModel: RegisterUiModel) {
        _state.value = RegisterState.Default(uiModel)
    }

    private fun updateEmailPassState() {
        _state.update { prevState ->
            check(prevState is RegisterState.Default)
            prevState.copy(
                uiModel = prevState.uiModel.copy(
                    emailCheckMessage = "사용 가능한 이메일입니다.",
                    emailCheckState = CheckState.PASS,
                    isCheckEmail = true,
                    isRegisterEnable = prevState.uiModel.onCheckRegisterEnabled()
                )
            )
        }
        _state.update {
            check(it is RegisterState.Default)
            it.copy(
                uiModel = it.uiModel.copy(
                    isRegisterEnable = it.uiModel.onCheckRegisterEnabled()
                )
            )
        }
    }
}
