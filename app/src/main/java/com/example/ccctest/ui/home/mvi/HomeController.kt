package com.enbd.learning.ui.login.mvi

import android.app.Application

import com.enbd.learning.ui.base.mvi.MviBaseController
import com.enbd.learning.ui.base.mvi.MviIntent

import com.example.ccctest.room.AppDatabase
import com.example.ccctest.room.entity.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HomeController : MviBaseController<HomeState>() {
    override fun execute(intent: MviIntent, application: Application): Observable<HomeState> =
        Observable.just(intent).flatMap { incomingIntent ->
            when (incomingIntent) {
                is HomeIntent.TimerIntent -> performTimer(incomingIntent.delay)
                is HomeIntent.GetUserData -> getUserData(
                    application,
                    incomingIntent.userId
                )
                is HomeIntent.UpdateUser -> updateUser(application, incomingIntent.user)
                else -> null
            }
        }

    private fun performTimer(delay: Long): Observable<HomeState>? =
        Observable.just(LoginState.TimerState).delay(delay, TimeUnit.MILLISECONDS).cast(
            HomeState::class.java
        )

    private fun getUserData(
        application: Application,
        userId: Long
    ): Observable<HomeState> {
        return AppDatabase.getDatabase(application).userDao()
            .findUserById(userId)
            .map { HomeState.UserData(it) }
            .cast(HomeState::class.java)
            .onErrorReturn { HomeState.Error("User does not exist") }
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun updateUser(
        application: Application,
        user: User
    ): Observable<HomeState> {
        return AppDatabase.getDatabase(application).userDao()
            .update(user)
            .map { HomeState.UserUpdate(it) }
            .cast(HomeState::class.java)
            .onErrorReturn { HomeState.Error("User update failed") }
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
