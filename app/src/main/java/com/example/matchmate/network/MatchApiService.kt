package com.example.matchmate.network

import com.example.matchmate.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApiService {
    @GET("api/")
    suspend fun getUsers(@Query("results") results: Int = 10): Response<UserResponse>
}