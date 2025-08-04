package com.example.matchmate.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.model.MatchProfile
import com.example.matchmate.model.MatchStatus
import com.example.matchmate.repository.UserRepository
import com.example.matchmate.util.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: UserRepository,
    private val context: Context
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
                if (NetworkUtils.isNetworkAvailable(context)) {
                    repository.fetchUsersFromApiAndCache()
                } else {
                    Toast.makeText(context, "You're offline. Showing cached matches.", Toast.LENGTH_SHORT).show()
                    Log.d("MainViewModel", "Offline mode: loading from DB only")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failed to fetch users: ${e.localizedMessage}")
            }
        }
    }

    fun updateMatchStatus(userId: String, status: MatchStatus) {
        viewModelScope.launch {
            if (NetworkUtils.isNetworkAvailable(context)) {
                repository.updateUserStatus(userId, status)
            } else {
                repository.queueUserStatus(userId, status)
            }
        }
    }

    fun updatePendingIfOnline() {
        viewModelScope.launch {
            if (NetworkUtils.isNetworkAvailable(context)) {
                repository.flushPendingActions()
            }
        }
    }

    fun refreshFromNetwork() {
        viewModelScope.launch {
            repository.clearUsersFromDb() // wipe local
            repository.fetchUsersFromApiAndCache() // refill
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            try {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    repository.fetchUsersFromApiAndCache()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Pagination failed: ${e.localizedMessage}")
            }
        }
    }
}