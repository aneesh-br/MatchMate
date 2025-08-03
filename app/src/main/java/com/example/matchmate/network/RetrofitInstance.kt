package com.example.matchmate.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //generates random user profiles.
    private const val BASE_URL = "https://randomuser.me/"

    val api: MatchApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MatchApiService::class.java)
    }
}