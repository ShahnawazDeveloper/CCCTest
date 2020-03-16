package com.enbd.learning.utils


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ccctest.R
import java.util.regex.Matcher
import java.util.regex.Pattern

object Utils {

    fun isInternetOn(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }


    fun setValueToView(view: View?, text: String?) {
        var textString = text


        if (view == null) {
            return
        }
        if (textString == null) {
            return
        }
        if (textString.contains("null")) {
            textString = textString.replace("null", "")
        }
        if (view is EditText) {

            view.setText(textString)

        } else if (view is TextView) {

            view.text = textString
        }


    }

    fun getValueFromView(view: View): String? {

        if (view is EditText) {

            return if (view.text.isNotEmpty()) view.text.toString() else null

        } else if (view is TextView) {

            return if (view.text.isNotEmpty()) view.text.toString() else null
        }


        return null
    }

    fun getColor(context: Context, id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }

    fun getColor(color: String?): Int? {
        return try {
            Color.parseColor(color)
        } catch (e: Exception) {
            null
        }
    }

    fun getDrawable(context: Context, id: Int): Drawable? {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getDrawable(context, id)
        } else {
            context.resources.getDrawable(id)
        }
    }

    fun getDimen(context: Context, id: Int): Int {
        return context.resources.getDimension(id).toInt()
    }


    fun getDummyStringList(): List<String> {
        val dummy: ArrayList<String> = ArrayList()
        for (i in 1..15) {
            dummy.add("hello " + (i * i))
        }
        return dummy
    }

    fun showProgressDialog(context: Context): Dialog {
        //=====================================================
        // loader was not visible if the background colour is same as loader colour.
        // Added dark overlay for dialog for solving this issue
        var theme = android.R.style.Theme_Translucent_NoTitleBar

        //================================================
        val dialog = Dialog(
            context,
            theme
        )
        //dialog.setContentView(R.layout.layout_custom_progress)

        dialog.setCancelable(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            dialog.window?.statusBarColor =
                ContextCompat.getColor(context, R.color.colorPrimary)
        }

        dialog.show()
        return dialog
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val passwordPattern = "^(?=.{8,})(?=.*[@#\$%^&+=]).*\$"
        pattern = Pattern.compile(passwordPattern)
        matcher = pattern.matcher(password.toString())
        return matcher.matches()
    }

    fun isValidPhoneNumber(phoneNumber: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val numberPattern = "^(01)[0-46-9]*[0-9]{7,8}\$"
        pattern = Pattern.compile(numberPattern)
        matcher = pattern.matcher(phoneNumber.toString())
        return matcher.matches()
    }

}