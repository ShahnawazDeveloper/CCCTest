package com.enbd.learning.ui.login.mvi

import android.app.Application
import com.enbd.learning.api.ENRetrofitClient
import com.enbd.learning.ui.base.mvi.MviBaseController
import com.enbd.learning.ui.base.mvi.MviIntent
import com.enbd.learning.utils.Utils
import com.example.ccctest.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ImageDisplayController : MviBaseController<ImageDisplayState>() {
    override fun execute(
        intent: MviIntent,
        application: Application
    ): Observable<ImageDisplayState> =
        Observable.just(intent).flatMap { incomingIntent ->
            when (incomingIntent) {
                is ImageDisplayIntent.TimerIntent -> performTimer(incomingIntent.delay)
                is ImageDisplayIntent.GetImages -> getImages(application)
                else -> null
            }
        }

    private fun performTimer(delay: Long): Observable<ImageDisplayState>? =
        Observable.just(ImageDisplayState.TimerState).delay(delay, TimeUnit.MILLISECONDS).cast(
            ImageDisplayState::class.java
        )


    private fun getImages(
        application: Application
    ): Observable<ImageDisplayState> {
        return ENRetrofitClient.create(application).getImages()
            .doOnError { }
            .map { ImageDisplayState.GetImages(it) }
            .cast(ImageDisplayState::class.java)
            .onErrorReturn {
                if (Utils.isInternetOn(application)) {
                    ImageDisplayState.Failure("Something went wrong please try again")
                } else {

                    ImageDisplayState.Failure(application.resources.getString(R.string.no_internet))
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .startWith(ImageDisplayState.Loading)
    }
}
