package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviViewState


sealed class SignUpState : MviViewState {
    object TimerState : SignUpState()
    object Loading : SignUpState()
    data class Error(val errorData: String?) : SignUpState()
    data class IsUserAlreadyExist(val isUserExist: Boolean) : SignUpState()
    data class InsertSuccess(val userId: Long) : SignUpState()


}
