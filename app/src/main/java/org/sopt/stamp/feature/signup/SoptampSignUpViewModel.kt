package org.sopt.stamp.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            is SignUpAction.PutPassword -> putPassword(action.input)
            is SignUpAction.PutPasswordConfirm -> putPasswordConfirm(action.input)
            is SignUpAction.SignUp -> signUp(action.nickname, action.email, action.password)
            is SignUpAction.CheckNickname -> checkNickname()
            is SignUpAction.CheckEmail -> checkEmail()
            is SignUpAction.CheckPassword -> checkPassword()
        }
    }

    private fun signUp(nickname: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.signup(nickname, email, password, "android", "null").let { res ->
                res.message.let {
                    _viewState.update { prevState ->
                        prevState.copy(
                            errorMessage = it
                        )
                    }
                }
                if (res.statusCode == 200) {
                    _singleEvent.trySend(SingleEvent.SignUpSuccess)
                }
            }
        }
    }

    private fun checkNickname() {
        viewModelScope.launch {
            viewState.value.nickname?.let {
                userRepository.checkNickname(it).let { res ->
                    res.message.let {
                        _viewState.update { prevState ->
                            prevState.copy(
                                errorMessage = it
                            )
                        }
                    }
                    if (res.statusCode == 200) {
                        _singleEvent.trySend(SingleEvent.CheckNicknameSuccess)
                    }
                }
            }
        }
    }

    private fun checkEmail() {
        viewModelScope.launch {
            viewState.value.email?.let {
                userRepository.checkEmail(it).let { res ->
                    res.message.let {
                        _viewState.update { prevState ->
                            prevState.copy(
                                errorMessage = it
                            )
                        }
                    }
                    if (res.statusCode == 200) {
                        _singleEvent.trySend(SingleEvent.CheckEmailSuccess)
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun checkPassword() {
        viewState.debounce(200)
            .onEach { state ->
                if (!state.password.isNullOrBlank() && !state.passwordConfirm.isNullOrBlank() && (state.password == state.passwordConfirm)) {
                    _viewState.update { prevState ->
                        prevState.copy(
                            errorMessage = ""
                        )
                    }
                } else {
                    _viewState.update { prevState ->
                        prevState.copy(
                            errorMessage = ""
                        )
                    }
                }
            }.launchIn(viewModelScope)

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

    private fun putPassword(input: String) {
        _viewState.update { prevState ->
            prevState.copy(
                password = input
            )
        }
        checkPassword()
    }

    private fun putPasswordConfirm(input: String) {
        _viewState.update { prevState ->
            prevState.copy(
                passwordConfirm = input
            )
        }
        checkPassword()
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
    data class SignUp(val nickname: String, val email: String, val password: String) : SignUpAction
}

sealed interface SingleEvent {
    object Loading : SingleEvent
    object SignUpSuccess : SingleEvent
    object CheckNicknameSuccess : SingleEvent
    object CheckEmailSuccess : SingleEvent
}
