package org.sopt.stamp.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.stamp.data.repository.RemoteUserRepository
import javax.inject.Inject

class SoptampLoginViewModel @Inject constructor(
    private val userRepository: RemoteUserRepository
) : ViewModel(), LoginHandleAction {

    private val _viewState = MutableStateFlow(SoptampLoginViewState.init())
    val viewState = _viewState.asStateFlow()

    private val _singleEvent = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent = _singleEvent.receiveAsFlow()

    override fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login(action.id, action.password)
        }
    }

    private fun login(id: String, password: String) {
        viewModelScope.launch {
            userRepository.login(id, password).let { res ->
                res.message?.let { msg ->
                    _viewState.update { prevState ->
                        prevState.copy(
                            errorMessage = msg
                        )
                    }
                    if (res.statusCode == 200) {
                        _singleEvent.trySend(SingleEvent.LoginSuccess)
                    }
                }
            }
        }
    }
}

interface LoginHandleAction {
    fun handleAction(action: LoginAction)
}

sealed interface LoginAction {
    data class Login(val id: String, val password: String) : LoginAction
}

sealed interface SingleEvent {
    object Loading : SingleEvent
    object LoginSuccess : SingleEvent
    object NavigateToJoin : SingleEvent
    object NavigateToFindAccount : SingleEvent
}
