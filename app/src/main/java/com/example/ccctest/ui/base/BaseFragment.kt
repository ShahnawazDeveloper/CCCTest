package com.enbd.learning.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder

import com.enbd.learning.utils.Utils
import kotlinx.android.synthetic.main.layout_custom_progress.*

abstract class BaseFragment : Fragment() {
    lateinit var activityContext: AppCompatActivity
    private var progressDialogue: Dialog? = null
    lateinit var unBinder: Unbinder

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context as AppCompatActivity
    }

    abstract fun getLayoutId(): Int
    abstract fun initViews()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unBinder = ButterKnife.bind(this, view)
        initViews()
    }

    override fun onDestroyView() {
        unBinder.unbind()
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    fun requestDidStart() {
        /**
         * Starting the progressing indicator
         */
        if (progressDialogue != null) {
            if (progressDialogue!!.isShowing) {
            }
        } else {
            try {
                progressDialogue = Utils.showProgressDialog(activityContext)
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