package com.immortalidiot.roomintroduction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.immortalidiot.roomintroduction.ui.theme.RoomIntroductionTheme
import com.immortalidiot.roomintroduction.ui.user.UserScreen
import com.immortalidiot.roomintroduction.ui.user.UserScreenViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userViewModel: UserScreenViewModel = koinViewModel()

            RoomIntroductionTheme {
                UserScreen(
                    viewModel = userViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
