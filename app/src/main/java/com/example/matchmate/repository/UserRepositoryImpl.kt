package com.example.matchmate.repository

import android.util.Log
import com.example.matchmate.model.UserResponse
import com.example.matchmate.network.MatchApiService
import com.example.matchmate.data.MatchProfileDao
import com.example.matchmate.data.MatchProfileEntity
import com.example.matchmate.data.PendingActionEntity
import com.example.matchmate.model.Dob
import com.example.matchmate.model.Location
import com.example.matchmate.model.Login
import com.example.matchmate.model.MatchProfile
import com.example.matchmate.model.MatchStatus
import com.example.matchmate.model.Name
import com.example.matchmate.model.Picture
import com.example.matchmate.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class UserRepositoryImpl(
    private val apiService: MatchApiService,
    private val matchProfileDao: MatchProfileDao
) : UserRepository {

    override suspend fun fetchUsersFromApiAndCache() {
        val response = fetchUsersFromApi()
        if (response.isSuccessful) {
            response.body()?.results?.let { users ->
                val entities = users.map {
                    MatchProfileEntity(
                        uuid = it.login.uuid,
                        name = "${it.name.first} ${it.name.last}",
                        age = it.dob.age,
                        city = it.location.city,
                        state = it.location.state,
                        country = it.location.country,
                        imageUrl = it.picture.large,
                        status = MatchStatus.PENDING
                    )
                }
                insertUsersToDb(entities)
            }
        } else {
            Log.e("UserRepositoryImpl", "API Error: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun fetchUsersFromApi(): Response<UserResponse> {
        return apiService.getUsers()
    }

    override suspend fun insertUsersToDb(users: List<MatchProfileEntity>) {
        matchProfileDao.insertProfiles(users)
    }

    override suspend fun updateUserStatus(userId: String, status: MatchStatus) {
        val user = matchProfileDao.getProfileById(userId)
        if (user != null) {
            val updatedUser = user.copy(status = status)
            matchProfileDao.updateProfile(updatedUser)
        }
    }

    override fun getUsersFromDb(): Flow<List<MatchProfile>> {
        return matchProfileDao.getAllProfiles().map { list ->
            list.map { it.toMatchProfile() }
        }
    }

    override suspend fun clearUsersFromDb() {
        matchProfileDao.clearAll()
    }

    override suspend fun queueUserStatus(userId: String, status: MatchStatus) {
        matchProfileDao.insertPendingAction(
            PendingActionEntity(userId = userId, status = status)
        )
    }

    override suspend fun flushPendingActions() {
        val pending = matchProfileDao.getAllPendingActions()
        for (action in pending) {
            val user = matchProfileDao.getProfileById(action.userId)
            if (user != null) {
                val updated = user.copy(status = action.status)
                matchProfileDao.updateProfile(updated)
            }
        }
        matchProfileDao.clearPendingActions()
    }

    fun MatchProfileEntity.toMatchProfile(): MatchProfile {
        return MatchProfile(
            user = User( // You may need to store more info in entity if you want richer domain objects
                gender = "",
                name = Name("", name.split(" ").first(), name.split(" ").last()),
                location = Location(city, state, country),
                email = "",
                dob = Dob("", age),
                phone = "",
                picture = Picture(imageUrl, imageUrl, imageUrl),
                login = Login(uuid)
            ),
            status = status
        )
    }

}