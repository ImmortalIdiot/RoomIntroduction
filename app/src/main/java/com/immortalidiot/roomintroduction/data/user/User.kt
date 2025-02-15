package com.immortalidiot.roomintroduction.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class User(
    val name: String,
    val registrationTime: Instant,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
