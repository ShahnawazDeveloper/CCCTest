package com.enbd.learning.utils

import android.content.Context
import android.content.SharedPreferences

object Preference {
    private const val PREFERENCE_KEY = "ENPreference"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    @Synchronized
    fun setUserId(context: Context, userId: Long) {
        val preferences = getSharedPreferences(context)
        val editor = preferences.edit()
        editor.putLong("USER_ID", userId).apply()
    }

    fun getUserId(context: Context): Long? {
        val preferences = getSharedPreferences(context)
        return preferences.getLong("USER_ID", 0)
    }

    @Synchronized
    fun clearPreference(context: Context) {
        getSharedPreferences(context).edit().clear().apply()
    }

}