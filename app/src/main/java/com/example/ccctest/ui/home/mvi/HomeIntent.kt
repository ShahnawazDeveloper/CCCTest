package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviIntent
import com.example.ccctest.room.entity.User


sealed class HomeIntent : MviIntent {
    data class TimerIntent(val delay: Long) : HomeIntent()
    data class GetUserData(val userId: Long) : HomeIntent()
    data class UpdateUser(val user: User) : HomeIntent()

}
