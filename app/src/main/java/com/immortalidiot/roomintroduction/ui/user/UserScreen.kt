package com.immortalidiot.roomintroduction.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.immortalidiot.roomintroduction.data.di.databaseModule
import com.immortalidiot.roomintroduction.ui.components.UserItem
import com.immortalidiot.roomintroduction.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserScreenViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.padding(top = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        when (uiState) {
            is UserScreenUiState.Loading -> {
                Text(text = "Loading")
            }
            is UserScreenUiState.NoEntities -> {
                Text(text = "No entries")
            }
            is UserScreenUiState.Loaded -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = (uiState as UserScreenUiState.Loaded).entities,
                        key = { user -> user.id }
                    ) { user ->
                        UserItem(user = user)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    KoinApplication(application = {
        androidLogger(Level.DEBUG)
        androidContext(context)
        modules(uiModule, databaseModule)
    }) {
        val viewModel: UserScreenViewModel = koinViewModel()
        UserScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
    }
}
