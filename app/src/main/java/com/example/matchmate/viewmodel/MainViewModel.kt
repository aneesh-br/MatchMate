package com.example.matchmate.viewmodel

import com.example.matchmate.repository.UserRepository
import com.example.matchmate.repository.UserRepositoryImpl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.model.MatchProfile
import com.example.matchmate.model.MatchStatus
import com.example.matchmate.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository: UserRepository = UserRepositoryImpl(RetrofitInstance.api)

    private val _users = MutableStateFlow<List<MatchProfile>>(emptyList())
    val users: StateFlow<List<MatchProfile>> = _users

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = repository.getUsers()
                if (response.isSuccessful) {
                    val matchProfiles = response.body()?.results?.map { user -> MatchProfile(user) }
                    _users.value = matchProfiles ?: emptyList()
                    Log.d("MainViewModel", "Fetched users: ${_users.value}")
                } else {
                    Log.e("MainViewModel", "API Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception: ${e.localizedMessage}")
            }
        }
    }


    fun updateMatchStatus(userId: String, status: MatchStatus) {
        _users.update { list ->
            list.map {
                if (it.user.login.uuid == userId) it.copy(status = status)
                else it
            }
        }
    }
}