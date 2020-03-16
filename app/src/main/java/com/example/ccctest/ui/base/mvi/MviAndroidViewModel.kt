package com.enbd.learning.ui.base.mvi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

open class MviAndroidViewModel<I : MviIntent, S : MviViewState, C : MviBaseController<S>>(
    application: Application,
    private val controller: Class<C>
) :
    AndroidViewModel(application), MviViewModel<I, S> {
    private val compositeDisposable = CompositeDisposable()
    private var intentSubject = PublishSubject.create<I>()
    fun unBind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun addToDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun processIntents(intents: Observable<I>) {
        compositeDisposable.add(intents.subscribe(intentSubject::onNext))
    }

    override fun states(): Observable<S> =
        intentSubject.map { it }.compose { intents ->
            intents.publish { shared ->
                shared.flatMap { controller.newInstance().execute(it, getApplication()) }
            }
        }
            .observeOn(AndroidSchedulers.mainThread())

}
