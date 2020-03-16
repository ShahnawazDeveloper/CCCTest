package com.example.ccctest.ui.login

import android.content.Intent
import android.util.Patterns
import butterknife.OnClick
import com.enbd.learning.ui.base.mvi.ENBaseViewModelActivity
import com.enbd.learning.ui.login.mvi.LoginController
import com.enbd.learning.ui.login.mvi.LoginIntent
import com.enbd.learning.ui.login.mvi.LoginState
import com.enbd.learning.utils.Preference
import com.enbd.learning.utils.Utils
import com.example.ccctest.R
import com.example.ccctest.ui.home.HomeActivity
import com.example.ccctest.ui.signup.SignUpActivity
import com.hayati.staffapp.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity :
    ENBaseViewModelActivity<LoginIntent, LoginState, LoginController>(LoginController::class.java) {

    override fun getIntentData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews() {
        if (Preference.getUserId(activityContext)!! > 0) {
            gotoHome()
        }
    }

    override fun processState(state: LoginState) {
        when (state) {
            is LoginState.Loading -> requestDidStart()
            is LoginState.Error -> {
                requestDidFinish()
                state.errorData?.let { showToast(it) }
            }
            is LoginState.LoginUser -> {
                requestDidFinish()
                state.user?.let {
                    showToast(getString(R.string.msg_login_success))
                    Preference.setUserId(activityContext, it.uid)
                    gotoHome()
                }
            }
        }
    }

    override fun onViewModelReady() {
    }

    private fun validateData(): Boolean {
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

    @OnClick(R.id.btnSignUp)
    fun signUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    @OnClick(R.id.btnLogin)
    fun login() {
        if (validateData()) {
            invokeIntent(
                LoginIntent.LoginUser(
                    Utils.getValueFromView(etEmail)!!,
                    Utils.getValueFromView(etPassword)!!
                )
            )
        }
    }

    private fun gotoHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()

    }
}
