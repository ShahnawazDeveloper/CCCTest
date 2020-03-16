package com.example.ccctest.ui.signup

import android.content.Intent
import android.util.Patterns
import butterknife.OnClick
import com.enbd.learning.ui.base.mvi.ENBaseViewModelActivity
import com.enbd.learning.ui.login.mvi.SignUpController
import com.enbd.learning.ui.login.mvi.SignUpIntent
import com.enbd.learning.ui.login.mvi.SignUpState
import com.enbd.learning.utils.Preference
import com.enbd.learning.utils.Utils
import com.example.ccctest.R
import com.example.ccctest.room.entity.User
import com.example.ccctest.ui.home.HomeActivity
import com.hayati.staffapp.utils.showToast
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : //ENBaseActivity() {
    ENBaseViewModelActivity<SignUpIntent, SignUpState, SignUpController>(SignUpController::class.java) {

    override fun getIntentData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_signup
    }

    override fun initViews() {
    }

    private fun validateData(): Boolean {
        /*if (ENUtils.getValueFromView(etFirstName).isNullOrEmpty()) {
            showToast("Please Enter First Name.")
            etFirstName.requestFocus()
            return false
        }

        if (ENUtils.getValueFromView(etLastName).isNullOrEmpty()) {
            showToast("Please Enter Last Name.")
            etLastName.requestFocus()
            return false
        }*/

        if (Utils.getValueFromView(etEmail).isNullOrEmpty()) {
            showToast(getString(R.string.error_msg_complete_form))
            etEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Utils.getValueFromView(etEmail).toString())
                .matches()
        ) {
            showToast(getString(R.string.error_msg_invalid_email))
            etEmail.requestFocus()
            return false
        }

        if (Utils.getValueFromView(etMobileNumber).isNullOrEmpty()) {
            showToast(getString(R.string.error_msg_complete_form))
            etMobileNumber.requestFocus()
            return false
        }

        if (!Utils.isValidPhoneNumber(Utils.getValueFromView(etMobileNumber))) {
            showToast(getString(R.string.error_msg_invalid_number))
            etMobileNumber.requestFocus()
            return false
        }

        if (Utils.getValueFromView(etPassword).isNullOrEmpty()) {
            showToast(getString(R.string.error_msg_complete_form))
            etPassword.requestFocus()
            return false
        }

        if (!Utils.isValidPassword(Utils.getValueFromView(etPassword))) {
            showToast(getString(R.string.error_msg_invalid_password))
            etPassword.requestFocus()
            return false
        }

        return true
    }

    @OnClick(R.id.btnRegister)
    fun onRegisterClick() {
        if (validateData()) {
            invokeIntent(
                SignUpIntent.IsUserAlreadyExist(
                    Utils.getValueFromView(etEmail).toString()
                )
            )
        }
    }

    private fun gotoHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()

    }

    override fun processState(state: SignUpState) {
        when (state) {
            is SignUpState.Loading -> requestDidStart()
            is SignUpState.Error -> {
                requestDidFinish()
                state.errorData?.let { showToast(it) }
            }
            is SignUpState.IsUserAlreadyExist -> {
                requestDidFinish()
                if (state.isUserExist) {
                    showToast("User already exist")
                } else {
                    invokeIntent(
                        SignUpIntent.InsertOrUpdateUser(
                            User(
                                0,
                                Utils.getValueFromView(etFirstName),
                                Utils.getValueFromView(etLastName),
                                Utils.getValueFromView(etEmail),
                                Utils.getValueFromView(etMobileNumber),
                                spGender.selectedItem.toString(),
                                Utils.getValueFromView(etPassword)
                            )
                        )
                    )
                }
            }
            is SignUpState.InsertSuccess -> {
                requestDidFinish()
                showToast("User created success")
                Preference.setUserId(activityContext, state.userId)
                gotoHome()
            }
        }
    }

    override fun onViewModelReady() {

    }

}
