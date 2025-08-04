package com.example.matchmate.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.matchmate.ui.components.MatchCard
import com.example.matchmate.viewmodel.MainViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MatchListScreen(viewModel: MainViewModel) {
    val users by viewModel.users.collectAsState()
    Log.d("MLS", "Fetched users: ${users}")
    var isRefreshing by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.refreshFromNetwork()
            isRefreshing = false
        }
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItem ->
                if (lastVisibleItem != null && lastVisibleItem >= users.size - 1 && !isLoadingMore) {
                    isLoadingMore = true
                    coroutineScope.launch {
                        viewModel.fetchNextPage()
                        isLoadingMore = false
                    }
                }
            }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(state = listState) {
            items(users) { profile ->
                MatchCard(
                    profile = profile,
                    onActionClick = { userId, status ->
                        viewModel.updateMatchStatus(userId, status)
                    }
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(androidx.compose.ui.Alignment.TopCenter)
        )
    }
}