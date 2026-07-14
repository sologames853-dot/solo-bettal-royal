package com.solofirebettelroyal.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 1. For Local Testing with Emulator: Use "http://10.0.2.2:5000/api/"
    // 2. For Production (Render): Use your Render URL (e.g., "https://solofire-backend.onrender.com/api/")
    private const val BASE_URL = "http://10.0.2.2:5000/api/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
