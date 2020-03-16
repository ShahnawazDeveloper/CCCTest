package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviViewState
import com.example.ccctest.room.entity.User


sealed class HomeState : MviViewState {
    object TimerState : HomeState()
    object Loading : HomeState()
    data class Error(val errorData: String?) : HomeState()
    data class UserData(val user: User?) : HomeState()
    data class UserUpdate(val userId: Int?) : HomeState()


}
