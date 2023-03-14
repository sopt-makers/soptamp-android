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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.stamp.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), SignUpHandleAction {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SingleEvent>(Channel.BUFFERED)
    val isSignUpSuccess = _uiEvent.receiveAsFlow()
        .map { it is SingleEvent.SignUpSuccess }
    val isSubmitEnabled = uiState.map {
        !it.email.isNullOrBlank() && !it.nickname.isNullOrBlank() && !it.password.isNullOrBlank() &&
            !it.passwordConfirm.isNullOrBlank() && (it.password == it.passwordConfirm)
    }

    init {
        uiState.debounce(200)
            .filter {
                !it.password.isNullOrBlank() && !it.passwordConfirm.isNullOrBlank() && (it.password == it.passwordConfirm)
            }
            .onEach {
                _uiState.update { it.copy(errorMessage = "") }
            }.launchIn(viewModelScope)
    }

    override fun handleAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.PutNickname -> putNickname(action.input)
            is SignUpAction.PutEmail -> putEmail(action.input)
            is SignUpAction.PutPassword -> putPassword(action.input)
            is SignUpAction.PutPasswordConfirm -> putPasswordConfirm(action.input)
            is SignUpAction.SignUp -> signUp()
            is SignUpAction.CheckNickname -> checkNickname()
            is SignUpAction.CheckEmail -> checkEmail()
            else -> {}
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            userRepository.signup(
                uiState.value.nickname.orEmpty(),
                uiState.value.email.orEmpty(),
                uiState.value.password.orEmpty(),
                "android",
                "null"
            ).let { res ->
                res.message.let {
                    _uiState.update { prevState ->
                        prevState.copy(
                            errorMessage = it
                        )
                    }
                }
                if (res.statusCode == 200) {
                    _uiEvent.trySend(SingleEvent.SignUpSuccess)
                }
            }
        }
    }

    private fun checkNickname() {
        viewModelScope.launch {
            uiState.value.nickname?.let {
                runCatching {
                    userRepository.checkNickname(it)
                }.onSuccess {
                    _uiEvent.trySend(SingleEvent.CheckNicknameSuccess)
                }.onFailure { error ->
                    Timber.e(error)
                    _uiState.update { state ->
                        state.copy(errorMessage = error.message)
                    }
                }
            }
        }
    }

    private fun checkEmail() {
        val email = uiState.value.email ?: ""
        viewModelScope.launch {
            runCatching {
                userRepository.checkEmail(email)
            }.onSuccess {
                _uiState.update { it.copy(email = email) }
            }.onFailure {
                _uiState.update { it.copy(errorMessage = it.errorMessage) }
            }
        }
    }

    private fun putNickname(input: String) {
        _uiState.update { prevState ->
            prevState.copy(
                nickname = input
            )
        }
    }

    private fun putEmail(input: String) {
        _uiState.update {
            it.copy(email = input)
        }
    }

    private fun putPassword(input: String) {
        _uiState.update { it.copy(password = input) }
    }

    private fun putPasswordConfirm(input: String) {
        _uiState.update { prevState ->
            prevState.copy(
                passwordConfirm = input
            )
        }
    }
}

interface SignUpHandleAction {
    fun handleAction(action: SignUpAction)
}

sealed interface SignUpAction {
    data class PutNickname(val input: String) : SignUpAction
    data class PutEmail(val input: String) : SignUpAction
    data class PutPassword(val input: String) : SignUpAction
    data class PutPasswordConfirm(val input: String) : SignUpAction
    object CheckNickname : SignUpAction
    object CheckEmail : SignUpAction
    object CheckPassword : SignUpAction
    object SignUp : SignUpAction
}

sealed interface SingleEvent {
    object SignUpSuccess : SingleEvent
    object CheckNicknameSuccess : SingleEvent
    object CheckEmailSuccess : SingleEvent
}
