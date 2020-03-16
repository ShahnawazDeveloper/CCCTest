package com.enbd.learning.ui.login.mvi

import android.app.Application
import com.enbd.learning.ui.base.mvi.MviBaseController
import com.enbd.learning.ui.base.mvi.MviIntent
import com.example.ccctest.room.AppDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginController : MviBaseController<LoginState>() {
    override fun execute(intent: MviIntent, application: Application): Observable<LoginState> =
        Observable.just(intent).flatMap { incomingIntent ->
            when (incomingIntent) {
                is LoginIntent.TimerIntent -> performTimer(incomingIntent.delay)
                is LoginIntent.LoginUser -> loginUser(
                    application,
                    incomingIntent.email,
                    incomingIntent.password
                )
                else -> null
            }
        }

    private fun performTimer(delay: Long): Observable<LoginState>? =
        Observable.just(LoginState.TimerState).delay(delay, TimeUnit.MILLISECONDS).cast(
            LoginState::class.java
        )

    private fun loginUser(
        application: Application,
        email: String,
        password: String
    ): Observable<LoginState> {
        return AppDatabase.getDatabase(application).userDao()
            .findUserByEmailAndPassword(email, password)
            .map { LoginState.LoginUser(it) }
            .cast(LoginState::class.java)
            .onErrorReturn { LoginState.Error("User does not exist") }
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
