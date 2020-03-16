package com.enbd.learning.ui.base.mvi

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.enbd.learning.ui.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class ENBaseViewModelActivity<I : MviIntent, S : MviViewState, C : MviBaseController<S>>(
    private var controllerClass: Class<C>
) : BaseActivity() {

    lateinit var viewModel: MviAndroidViewModel<I, S, C>
    abstract fun processState(state: S)
    abstract fun onViewModelReady()
    lateinit var publishSubject: PublishSubject<I>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        publishSubject = PublishSubject.create()
        viewModel =
            ViewModelProviders.of(
                this,
                CustomViewModelFactor<I, S, C>(application, controllerClass)
            ).get(MviAndroidViewModel::class.java) as MviAndroidViewModel<I, S, C>

        viewModel.addToDisposable(viewModel.states().subscribe {
            processState(it)
        })
        viewModel.processIntents(getIntents())
        onViewModelReady()
    }

    private fun getIntents(): Observable<I> = publishSubject

    fun invokeIntent(intent: I) {
        publishSubject?.onNext(intent)
    }

    override fun onDestroy() {
        viewModel.unBind()
        super.onDestroy()
    }

}

class CustomViewModelFactor<I : MviIntent, S : MviViewState, C : MviBaseController<S>>(
    private val application: Application,
    private val controllerClass: Class<C>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MviAndroidViewModel<I, S, C>(application, controllerClass) as T
    }
}