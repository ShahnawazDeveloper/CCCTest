package com.enbd.learning.ui.base.mvi

import android.app.Application
import io.reactivex.Observable


abstract class MviBaseController<S : MviViewState> {
  abstract fun execute(intent: MviIntent, application: Application): Observable<S>
}
