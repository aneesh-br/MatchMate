package com.example.matchmate.repository

import com.example.matchmate.data.MatchProfileEntity
import com.example.matchmate.model.MatchProfile
import com.example.matchmate.model.MatchStatus
import com.example.matchmate.model.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {

    suspend fun fetchUsersFromApiAndCache()

    suspend fun fetchUsersFromApi(): Response<UserResponse>

    suspend fun insertUsersToDb(users: List<MatchProfileEntity>)

    suspend fun updateUserStatus(userId: String, status: MatchStatus)

    fun getUsersFromDb(): Flow<List<MatchProfile>>

    suspend fun clearUsersFromDb()

    suspend fun queueUserStatus(userId: String, status: MatchStatus)

    suspend fun flushPendingActions()
}