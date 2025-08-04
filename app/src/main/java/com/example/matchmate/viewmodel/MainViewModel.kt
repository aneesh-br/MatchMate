package com.example.matchmate.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.model.MatchProfile
import com.example.matchmate.model.MatchStatus
import com.example.matchmate.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<MatchProfile>>(emptyList())
    val users: StateFlow<List<MatchProfile>> = _users

    init {
        fetchAndCacheUsers()
        observeUsers()
    }

    private fun observeUsers() {
        viewModelScope.launch {
            repository.getUsersFromDb().collectLatest { users ->
                _users.value = users
            }
        }
    }

    private fun fetchAndCacheUsers() {
        viewModelScope.launch {
            try {
                repository.fetchUsersFromApiAndCache()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failed to fetch users: ${e.localizedMessage}")
            }
        }
    }

    fun updateMatchStatus(userId: String, status: MatchStatus) {
        viewModelScope.launch {
            repository.updateUserStatus(userId, status)
        }
    }
}