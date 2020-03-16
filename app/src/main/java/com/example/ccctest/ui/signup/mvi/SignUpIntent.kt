package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviIntent
import com.example.ccctest.room.entity.User


sealed class SignUpIntent : MviIntent {
    data class TimerIntent(val delay: Long) : SignUpIntent()

    data class InsertOrUpdateUser(val user: User) : SignUpIntent()

    data class IsUserAlreadyExist(val email: String) : SignUpIntent()

}
