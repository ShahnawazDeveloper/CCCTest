package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviIntent


sealed class ImageDisplayIntent : MviIntent {
    data class TimerIntent(val delay: Long) : ImageDisplayIntent()

    object GetImages : ImageDisplayIntent()

}
