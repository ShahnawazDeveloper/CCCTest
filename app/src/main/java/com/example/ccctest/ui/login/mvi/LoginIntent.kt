package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviIntent


sealed class LoginIntent : MviIntent {
    data class TimerIntent(val delay: Long) : LoginIntent()

    data class LoginUser(val email: String, val password: String) : LoginIntent()
}
