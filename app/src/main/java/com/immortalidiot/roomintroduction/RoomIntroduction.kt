package com.immortalidiot.roomintroduction

import android.app.Application
import com.immortalidiot.roomintroduction.data.di.databaseModule
import com.immortalidiot.roomintroduction.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class RoomIntroduction : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@RoomIntroduction)
            modules(uiModule, databaseModule)
        }
    }
}
