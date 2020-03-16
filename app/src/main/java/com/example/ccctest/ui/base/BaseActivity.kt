package com.enbd.learning.ui.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife

import com.enbd.learning.utils.Utils
import kotlinx.android.synthetic.main.layout_custom_progress.*

abstract class BaseActivity : AppCompatActivity() {
    lateinit var activityContext: AppCompatActivity
    private var progressDialogue: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }*/
        super.onCreate(savedInstanceState)
        activityContext = this
        getIntentData()
        setContentView(getLayoutId())
        ButterKnife.bind(this)
        initViews()
    }

    abstract fun getIntentData()
    abstract fun getLayoutId(): Int
    abstract fun initViews()

    fun requestDidStart() {
        /**
         * Starting the progressing indicator
         */
        if (progressDialogue != null) {
            if (progressDialogue!!.isShowing) {
            }
        } else {
            try {
                progressDialogue = Utils.showProgressDialog(this)
                progressDialogue?.ballSpinFadeLoaderIndicator?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    fun requestDidFinish() {
        /**
         * Finishing the progressing indicator
         */

        if (progressDialogue != null) {
            if (progressDialogue!!.isShowing) {
                try {
                    progressDialogue?.ballSpinFadeLoaderIndicator?.hide()
                    progressDialogue!!.dismiss()
                    progressDialogue = null

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

}