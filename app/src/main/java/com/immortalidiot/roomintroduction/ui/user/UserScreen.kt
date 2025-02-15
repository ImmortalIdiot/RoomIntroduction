package com.immortalidiot.roomintroduction.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.immortalidiot.roomintroduction.ui.components.UserItem

@Composable
fun UserScreen(
    paddingValues: PaddingValues,
    viewModel: UserScreenViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.padding(paddingValues = paddingValues),
        contentAlignment = Alignment.TopCenter
    ) {
        when (uiState) {
            is UserScreenUiState.Loading -> {
                Text(text = "Loading")
            }
            is UserScreenUiState.NoEntries -> {
                Text(text = "No entries")
            }
            is UserScreenUiState.Loaded -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items((uiState as UserScreenUiState.Loaded).entries) { user ->
                        UserItem(user = user)
                    }
                }
            }
        }
    }
}
