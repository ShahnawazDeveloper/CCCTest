package com.enbd.learning.ui.login.mvi

import com.enbd.learning.ui.base.mvi.MviViewState
import com.example.ccctest.api.respone.gallery.GetImagesResponse


sealed class ImageDisplayState : MviViewState {
    object TimerState : ImageDisplayState()
    object Loading : ImageDisplayState()

    data class Failure(val errorData: String?) : ImageDisplayState()
    data class GetImages(val getImageResponse: GetImagesResponse) : ImageDisplayState()

}
