package com.example.matchmate.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.matchmate.ui.components.MatchCard
import com.example.matchmate.viewmodel.MainViewModel

@Composable
fun MatchListScreen(viewModel: MainViewModel) {
    val users by viewModel.users.collectAsState()
    Log.d("MLS", "Fetched users: ${users}")

    LazyColumn {
        items(users) { profile ->
            MatchCard(
                profile = profile,
                onActionClick = { userId, status ->
                    viewModel.updateMatchStatus(userId, status)
                }
            )
        }
    }
}