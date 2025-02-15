package com.immortalidiot.roomintroduction.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.immortalidiot.roomintroduction.data.Converters
import com.immortalidiot.roomintroduction.data.user.User
import com.immortalidiot.roomintroduction.data.user.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Database(
    version = 1,
    entities = [User::class]
)
@TypeConverters(Converters::class)
private abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserRepository(): UserRepository

    companion object {
        const val DATABASE_NAME = "UserDB"
    }
}

private fun provideUserDatabase(context: Context): UserDatabase {
    return Room.databaseBuilder(
        context = context,
        klass = UserDatabase::class.java,
        name = UserDatabase.DATABASE_NAME
    ).build()
}

val databaseModule = module {
    singleOf(::provideUserDatabase)
    single { get<UserDatabase>().getUserRepository() }
}
