package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviViewState
import com.example.ccctest.room.entity.User


sealed class LoginState : MviViewState {
    object TimerState : LoginState()

    object Loading : LoginState()
    data class Error(val errorData: String?) : LoginState()
    data class LoginUser(val user: User?) : LoginState()

}
