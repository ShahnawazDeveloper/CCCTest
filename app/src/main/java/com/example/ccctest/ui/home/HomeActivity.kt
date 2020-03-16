package com.example.ccctest.ui.home

import android.content.Intent
import butterknife.OnClick
import com.enbd.learning.ui.base.mvi.ENBaseViewModelActivity
import com.enbd.learning.ui.login.mvi.HomeController
import com.enbd.learning.ui.login.mvi.HomeIntent
import com.enbd.learning.ui.login.mvi.HomeState
import com.enbd.learning.utils.Preference
import com.enbd.learning.utils.Utils
import com.example.ccctest.R
import com.example.ccctest.room.entity.User
import com.example.ccctest.ui.image_displayer.ImageDisplayerActivity
import com.example.ccctest.ui.login.LoginActivity
import com.example.ccctest.utils.EditTextDialog
import com.hayati.staffapp.utils.showToast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity :
    ENBaseViewModelActivity<HomeIntent, HomeState, HomeController>(HomeController::class.java) {

    var user: User? = null

    override fun getIntentData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun initViews() {

    }

    override fun processState(state: HomeState) {
        when (state) {
            is HomeState.Loading -> requestDidStart()
            is HomeState.Error -> {
                requestDidFinish()
                state.errorData?.let { showToast(it) }
            }
            is HomeState.UserData -> {
                requestDidFinish()
                state.user?.let {
                    user = it
                    updateUI()
                }
            }
            is HomeState.UserUpdate -> {
                requestDidFinish()
                showToast(getString(R.string.msg_number_edited))
            }
        }
    }

    override fun onViewModelReady() {
        invokeIntent(HomeIntent.GetUserData(Preference.getUserId(activityContext)!!))
    }

    private fun updateUI() {
        user?.let {
            it.firstName?.let {
                tvName.text = it
            }
            it.lastName?.let { lname ->
                Utils.getValueFromView(tvName)?.let { value ->
                    tvName.text = "$value $lname"
                } ?: kotlin.run {
                    tvName.text = lname
                }
            }

            it.mobileNumber?.let {
                tvMobileNumber.text = it
            }
        }
    }

    @OnClick(R.id.btnGallery)
    fun onGalleryClick() {
        startActivity(Intent(this, ImageDisplayerActivity::class.java))
    }

    @OnClick(R.id.btnLogout)
    fun onLogoutClick() {
        Preference.clearPreference(activityContext)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    @OnClick(R.id.btnEditNumber)
    fun editNumberDialog() {
        val dialog = EditTextDialog.newInstance(
            title = getString(R.string.msg_change_number),
            text = user?.mobileNumber,
            hint = getString(R.string.mobile_number),
            isMultiline = false
        )
        dialog.onOk = {
            val number = dialog.editText.text
            user?.mobileNumber = number.toString()
            tvMobileNumber.text = number
            invokeIntent(HomeIntent.UpdateUser(user!!))
        }
        dialog.show(supportFragmentManager, "editDescription")
    }

}
