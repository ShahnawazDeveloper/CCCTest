package com.example.ccctest.ui.image_displayer

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enbd.learning.ui.base.mvi.ENBaseViewModelActivity
import com.enbd.learning.ui.login.mvi.ImageDisplayController
import com.enbd.learning.ui.login.mvi.ImageDisplayIntent
import com.enbd.learning.ui.login.mvi.ImageDisplayState
import com.example.ccctest.R
import com.example.ccctest.adapter.GalleryAdapter
import com.example.ccctest.api.respone.gallery.Photo
import com.hayati.staffapp.utils.showToast
import kotlinx.android.synthetic.main.activity_image_displayer.*


class ImageDisplayerActivity :
    ENBaseViewModelActivity<ImageDisplayIntent, ImageDisplayState, ImageDisplayController>(
        ImageDisplayController::class.java
    ) {

    var galleryAdapter: GalleryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_displayer)
    }

    override fun getIntentData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_image_displayer
    }

    override fun initViews() {
    }

    override fun processState(state: ImageDisplayState) {
        when (state) {
            is ImageDisplayState.Loading -> requestDidStart()
            is ImageDisplayState.Failure -> {
                requestDidFinish()
                state.errorData?.let { showToast(it) }
            }
            is ImageDisplayState.GetImages -> {
                requestDidFinish()
                state.getImageResponse.photos?.photo?.let {
                    setAdapter(it)
                }
            }
        }
    }

    override fun onViewModelReady() {
        invokeIntent(ImageDisplayIntent.GetImages)
    }

    private fun setAdapter(it: List<Photo>) {
        rvGallery.layoutManager = LinearLayoutManager(activityContext)

        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        rvGallery.setLayoutManager(mLayoutManager)

        galleryAdapter = GalleryAdapter(activityContext)
        rvGallery.adapter = galleryAdapter

        galleryAdapter!!.doRefresh(it)

    }

}
