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
import org.sopt.stamp.data.local.SoptampDataStore
import org.sopt.stamp.data.repository.RemoteUserRepository
import javax.inject.Inject

@HiltViewModel
class SoptampLoginViewModel @Inject constructor(
    private val userRepository: RemoteUserRepository,
    private val dataStore: SoptampDataStore
) : ViewModel(), LoginHandleAction {

    private val _viewState = MutableStateFlow(SoptampLoginViewState.init())
    val viewState = _viewState.asStateFlow()

    private val _singleEvent = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent = _singleEvent.receiveAsFlow()

    override fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login()
            is LoginAction.PutEmail -> putEmail(action.input)
            is LoginAction.PutPassword -> putPassword(action.input)
        }
    }

    private fun login() {
        viewModelScope.launch {
            userRepository.login(viewState.value.email.orEmpty(), viewState.value.password.orEmpty()).let { res ->
                res.message?.let { msg ->
                    _viewState.update { prevState ->
                        prevState.copy(
                            errorMessage = msg
                        )
                    }
                    if (res.statusCode == 200) {
                        _singleEvent.trySend(SingleEvent.LoginSuccess)
                        res.userId?.let { dataStore.userId = it }
                        res.profileMessage?.let { dataStore.profileMessage = it }
                        updateLoginComplete()
                    }
                }
            }
        }
    }

    private fun putEmail(input: String) {
        _viewState.update { prevState ->
            prevState.copy(
                email = input
            )
        }
    }

    private fun putPassword(input: String) {
        _viewState.update { prevState ->
            prevState.copy(
                password = input
            )
        }
    }

    private fun updateLoginComplete() {
        _viewState.update { prevState ->
            prevState.copy(
                isComplete = true
            )
        }
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
