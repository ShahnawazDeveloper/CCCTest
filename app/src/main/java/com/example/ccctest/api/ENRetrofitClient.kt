package com.enbd.learning.api

import android.content.Context
import com.example.ccctest.BuildConfig
import com.example.ccctest.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ENRetrofitClient {
    fun create(context: Context): ENAPIServices {

        val logInterceptor = ENLoggingInterceptor(context, true)
        logInterceptor.setLevel(ENLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).client(client)
            .build()
        return retrofit.create<ENAPIServices>(ENAPIServices::class.java)
    }

    class NoConnectivityException(private val context: Context) : IOException() {

        override fun getLocalizedMessage(): String {
            return context.getString(R.string.check_internet_connection)
        }
    }

}