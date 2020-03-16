package com.enbd.learning.ui.base.mvi

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.enbd.learning.ui.base.BaseFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


abstract class BaseViewModelFragment<I : MviIntent, S : MviViewState, C : MviBaseController<S>>(
    private var controllerClass: Class<C>
) : BaseFragment() {
    private lateinit var viewModel: MviAndroidViewModel<I, S, C>
    abstract fun onViewModelReady()
    abstract fun processState(state: S)
    private lateinit var publishSubject: PublishSubject<I>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        publishSubject = PublishSubject.create()
        viewModel =
            ViewModelProviders.of(
                this,
                CustomViewModelFactor<I, S, C>(activityContext.application, controllerClass)
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

    override fun onDestroyView() {
        viewModel.unBind()
        super.onDestroyView()

    }

}