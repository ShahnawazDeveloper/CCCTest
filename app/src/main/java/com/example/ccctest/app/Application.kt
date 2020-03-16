package com.enbd.learning.app

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class Application : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
    }

    companion object {
        lateinit var instance: Application
    }
}