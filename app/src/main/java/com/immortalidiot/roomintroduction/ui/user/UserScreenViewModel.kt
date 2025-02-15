package com.immortalidiot.roomintroduction.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.roomintroduction.data.user.User
import com.immortalidiot.roomintroduction.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserScreenViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserScreenUiState>(UserScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        _uiState.update { UserScreenUiState.Loading }
        viewModelScope.launch(Dispatchers.Default) {
            val users = async(Dispatchers.IO) {
                userRepository.getUsers()
            }.await()

            if (users.isEmpty()) {
                _uiState.update { UserScreenUiState.NoEntities }
            } else {
                users.sortedByDescending { it.registrationTime }
                _uiState.update { UserScreenUiState.Loaded(entities = users) }
            }
        }
    }
}

sealed interface UserScreenUiState {
    data object Loading : UserScreenUiState
    data object NoEntities : UserScreenUiState
    data class Loaded(val entities: List<User>) : UserScreenUiState
}
