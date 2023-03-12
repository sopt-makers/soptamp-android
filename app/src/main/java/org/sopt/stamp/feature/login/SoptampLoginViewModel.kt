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
package org.sopt.stamp.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.stamp.domain.repository.UserRepository
import org.sopt.stamp.domain.usecase.auth.AutoLoginUseCase
import javax.inject.Inject

@HiltViewModel
class SoptampLoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val autoLoginUseCase: AutoLoginUseCase
) : ViewModel(), LoginHandleAction {

    private val _uiState = MutableStateFlow(SoptampLoginViewState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SingleEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    override fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login()
            is LoginAction.PutEmail -> putEmail(action.input)
            is LoginAction.PutPassword -> putPassword(action.input)
        }
    }

    fun onAutoLogin() {
        val result = autoLoginUseCase()
        if (result) {
            updateLoginComplete()
        }
    }

    private fun login() {
        viewModelScope.launch {
            runCatching {
                userRepository.login(
                    uiState.value.email.orEmpty(),
                    uiState.value.password.orEmpty()
                )
            }.onSuccess {
                _uiEvent.trySend(SingleEvent.LoginSuccess)
                userRepository.updateLocalUserInfo(
                    it.userId ?: -1,
                    it.profileMessage ?: ""
                )
                updateLoginComplete()
            }.onFailure {
                _uiState.update { it.copy(errorMessage = it.errorMessage) }
            }
        }
    }

    private fun putEmail(input: String) {
        _uiState.update { it.copy(email = input) }
    }

    private fun putPassword(input: String) {
        _uiState.update { it.copy(password = input) }
    }

    private fun updateLoginComplete() {
        _uiState.update { it.copy(isComplete = true) }
    }
}

interface LoginHandleAction {
    fun handleAction(action: LoginAction)
}

sealed interface LoginAction {

    data class PutEmail(val input: String) : LoginAction
    data class PutPassword(val input: String) : LoginAction
    object Login : LoginAction
}

sealed interface SingleEvent {
    object Loading : SingleEvent
    object LoginSuccess : SingleEvent
    object NavigateToJoin : SingleEvent
    object NavigateToFindAccount : SingleEvent
}
