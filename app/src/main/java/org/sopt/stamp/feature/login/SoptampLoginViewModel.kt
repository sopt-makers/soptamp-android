package org.sopt.stamp.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SoptampLoginViewModel @Inject constructor() : ViewModel(), LoginHandleAction {

    private val _viewState = MutableStateFlow(SoptampLoginViewState.init())
    val viewState = _viewState.asStateFlow()

    private val _singleEvent = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent = _singleEvent.receiveAsFlow()

    override fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login(action.id, action.password)
            is LoginAction.Success -> TODO()
            is LoginAction.Fail -> TODO()
        }
    }

    private fun login(id: String, password: String) {
        viewModelScope.launch {
            // TODO login post
            // TODO viewstate update success || fail
        }
    }

}

interface LoginHandleAction {
    fun handleAction(action: LoginAction)
}

sealed interface LoginAction {
    data class Login(val id: String, val password: String) : LoginAction
    data class Success(val id: String, val password: String) : LoginAction
    data class Fail(val id: String, val password: String) : LoginAction
}

sealed interface SingleEvent {
    object Loading : SingleEvent
    object NavigateToJoin : SingleEvent
    object NavigateToFindAccount : SingleEvent
}
