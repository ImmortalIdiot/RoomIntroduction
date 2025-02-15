package com.immortalidiot.roomintroduction.data.user

import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.random.Random

object MockRepository : UserRepository {
    private var id: Long = 1
    private val random = Random(12345)

    private val MOCK_USERS = let {
        val result = mutableListOf<User>()
        repeat(14) {
            val year = random.nextInt(2020, 2025)
            val month = random.nextInt(1, 13)
            val day = random.nextInt(1, 29)
            repeat(random.nextInt(2, 5)) {
                result += getRandomUser(year, month, day)
            }
        }
        result
    }

    private fun getRandomUser(
        year: Int = random.nextInt(2020, 2025),
        month: Int = random.nextInt(1, 13),
        day: Int = random.nextInt(1, 29)
    ): User {
        val names = listOf("Elza", "Michael", "Luna", "John", "Rio", "Phoenix", "Seth", "Mira", "Lick")
        val hour = random.nextInt(6, 12)
        val minute = random.nextInt(0, 60)
        val second = random.nextInt(0, 60)

        return User(
            name = names.random(),
            registrationTime = LocalDateTime
                .of(year, month, day, hour, minute, second)
                .toInstant(ZoneOffset.UTC),
            id = id++
        )
    }

    override suspend fun saveUser(user: User) = Unit

    override suspend fun getUsers(): List<User> = MOCK_USERS

    override suspend fun getUserById(userId: Long) = MOCK_USERS.random()

    override suspend fun deleteUser(user: User) = Unit
}
