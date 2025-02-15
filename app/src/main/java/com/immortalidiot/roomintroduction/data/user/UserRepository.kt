package com.immortalidiot.roomintroduction.data.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserRepository {
    @Upsert
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM User WHERE User.id = :userId")
    suspend fun getUserById(userId: Long): User?

    @Delete
    suspend fun deleteUser(user: User)
}
