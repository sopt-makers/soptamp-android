package org.sopt.stamp.feature.signup

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SoptampSignUpViewModel {
    private val _viewState = MutableStateFlow(SoptampSignUpViewState.init())
    val viewState = _viewState.asStateFlow()

    private val _singleEvent = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent = _singleEvent.receiveAsFlow()

    sealed interface LoginAction {
        data class Success(val id: String, val password: String) : LoginAction
        data class Fail(val id: String, val password: String) : LoginAction
        object NavigateToJoin : LoginAction
        object NavigateToFindAccount : LoginAction
    }

    sealed interface SingleEvent {
        object Loading : SingleEvent
    }
}
