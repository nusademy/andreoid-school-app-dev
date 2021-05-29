package com.nusademy.nusademy.dataapi

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val MAIN_URL = "https://api.nusademy.id/"
    val retrofitBuilder = Retrofit.Builder().baseUrl(MAIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val instanceUserApi = retrofitBuilder.create(Api::class.java)
}