package com.example.matchmate.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.model.User
import com.example.matchmate.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUsers()
                if (response.isSuccessful) {
                    _users.value = response.body()?.results ?: emptyList()
                    Log.d("MainViewModel", "Fetched users: ${_users.value}")
                } else {
                    Log.e("MainViewModel", "API Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception: ${e.localizedMessage}")
            }
        }
    }
}