package com.enbd.learning.ui.login.mvi

import android.app.Application
import com.enbd.learning.ui.base.mvi.MviBaseController
import com.enbd.learning.ui.base.mvi.MviIntent
import com.example.ccctest.room.AppDatabase
import com.example.ccctest.room.entity.User
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SignUpController : MviBaseController<SignUpState>() {
    override fun execute(intent: MviIntent, application: Application): Observable<SignUpState> =
        Observable.just(intent).flatMap { incomingIntent ->
            when (incomingIntent) {
                is SignUpIntent.TimerIntent -> performTimer(incomingIntent.delay)
                is SignUpIntent.InsertOrUpdateUser -> insertUser(application, incomingIntent.user)
                is SignUpIntent.IsUserAlreadyExist -> checkUserExistence(
                    application,
                    incomingIntent.email
                )
                else -> null
            }
        }

    private fun performTimer(delay: Long): Observable<SignUpState>? =
        Observable.just(SignUpState.TimerState).delay(delay, TimeUnit.MILLISECONDS).cast(
            SignUpState::class.java
        )

    private fun insertUser(
        application: Application,
        user: User
    ): Observable<SignUpState> {
        return AppDatabase.getDatabase(application).userDao()
            .insertUser(user)
            .map { SignUpState.InsertSuccess(it) }
            .cast(SignUpState::class.java)
            .onErrorReturn { SignUpState.Error(it.message) }
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun checkUserExistence(
        application: Application,
        email: String
    ): Observable<SignUpState> {
        return AppDatabase.getDatabase(application).userDao()
            .findByEmail(email)
            .map { SignUpState.IsUserAlreadyExist(true) }
            .cast(SignUpState::class.java)
            .onErrorReturn { SignUpState.IsUserAlreadyExist(false) }
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
