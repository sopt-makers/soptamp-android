package org.sopt.stamp.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.stamp.data.repository.RemoteUserRepository
import timber.log.Timber
import javax.inject.Inject

class SoptampSignUpViewModel @Inject constructor(
    private val userRepository: RemoteUserRepository
) : ViewModel(), SignUpHandleAction {
    private val _viewState = MutableStateFlow(SoptampSignUpViewState.init())
    val viewState = _viewState.asStateFlow()

    private val _singleEvent = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent = _singleEvent.receiveAsFlow()

    override fun handleAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.PutNickname -> putNickname(action.input)
            is SignUpAction.PutEmail -> putEmail(action.input)
            is SignUpAction.PutPassword -> TODO()
            is SignUpAction.SignUp -> signUp(action.nickname, action.email, action.password)
            is SignUpAction.CheckNickname -> checkNickname()
            is SignUpAction.CheckEmail -> checkEmail()
            is SignUpAction.CheckPassword -> checkPassword(action.password)
        }
    }

    private fun signUp(nickname: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.signup(nickname, email, password, "android", "null")
                .onSuccess {
                    // 200
                    _singleEvent.trySend(SingleEvent.SignUpSuccess)

                    // 500
                    _viewState.update { prevState ->
                        prevState.copy(
                            errorMessage = it.message
                        )
                    }
                }
                .onFailure { Timber.tag("test").d(it) }
        }
    }

    private fun checkNickname() {
        viewModelScope.launch {
            viewState.value.nickname?.let {
                userRepository.checkNickname(it)
                    .onSuccess {
                        // 200
                        _singleEvent.trySend(SingleEvent.CheckNicknameSuccess)

                        // 400
                        _viewState.update { prevState ->
                            prevState.copy(
                                errorMessage = it.message
                            )
                        }
                    }
                    .onFailure { Timber.tag("test").d(it) }
            }
        }
    }

    private fun checkEmail() {
        viewModelScope.launch {
            viewState.value.email?.let {
                userRepository.checkEmail(it)
                    .onSuccess {
                        // 200
                        _singleEvent.trySend(SingleEvent.CheckEmailSuccess)

                        // 400
                        _viewState.update { prevState ->
                            prevState.copy(
                                errorMessage = it.message
                            )
                        }
                    }
                    .onFailure { Timber.tag("test").d(it) }
            }
        }
    }

    private fun checkPassword(password: String) {
        // TODO
    }

    private fun putNickname(input: String) {
        _viewState.update { prevState ->
            prevState.copy(
                nickname = input
            )
        }
    }

    private fun putEmail(input: String) {
        _viewState.update { prevState ->
            prevState.copy(
                email = input
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
    object CheckNickname : SignUpAction
    object CheckEmail : SignUpAction
    data class CheckPassword(val password: String, val passwordCheck: String) : SignUpAction
    data class SignUp(val nickname: String, val email: String, val password: String) : SignUpAction
}

sealed interface SingleEvent {
    object Loading : SingleEvent
    object SignUpSuccess : SingleEvent
    object CheckNicknameSuccess : SingleEvent
    object CheckEmailSuccess : SingleEvent
}
