package com.immortalidiot.roomintroduction.ui.user

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.immortalidiot.roomintroduction.data.di.databaseModule
import com.immortalidiot.roomintroduction.ui.components.UserItem
import com.immortalidiot.roomintroduction.ui.di.uiModule
import com.immortalidiot.roomintroduction.ui.theme.Orange
import kotlinx.coroutines.launch
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
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val username = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

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

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp, end = 32.dp)
                .size(64.dp),
            contentColor = Color.White,
            containerColor = Orange,
            shape = CircleShape,
            onClick = { showDialog.value = true }
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add User",
            )
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    showDialog.value = false
                    username.value = ""
                },
                title = { Text(text = "Enter User Name", fontWeight = FontWeight.Bold) },
                text = {
                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                if (username.value.isNotBlank()) {
                                    viewModel.saveUser(username.value)
                                    username.value = ""
                                    showDialog.value = false
                                }
                            }
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog.value = false
                            username.value = ""
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
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
