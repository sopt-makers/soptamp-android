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
import org.sopt.stamp.App
import org.sopt.stamp.data.repository.RemoteUserRepository
import javax.inject.Inject

@HiltViewModel
class SoptampLoginViewModel @Inject constructor(
    private val userRepository: RemoteUserRepository
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
                        res.userId?.let { App.getInstance().getDataStore().setUserId(it) }
                        res.profileMessage?.let { App.getInstance().getDataStore().setProfileMessage(it) }
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
