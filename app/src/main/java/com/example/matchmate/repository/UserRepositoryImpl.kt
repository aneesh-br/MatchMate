package com.example.matchmate.repository

import com.example.matchmate.model.UserResponse
import com.example.matchmate.network.MatchApiService
import retrofit2.Response

class UserRepositoryImpl(private val apiService: MatchApiService) : UserRepository {
    override suspend fun getUsers(): Response<UserResponse> {
        return apiService.getUsers()
    }
}