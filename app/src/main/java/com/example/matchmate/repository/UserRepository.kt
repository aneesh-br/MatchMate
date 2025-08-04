package com.example.matchmate.repository

import com.example.matchmate.model.UserResponse
import retrofit2.Response

interface UserRepository {
    suspend fun getUsers(): Response<UserResponse>
}