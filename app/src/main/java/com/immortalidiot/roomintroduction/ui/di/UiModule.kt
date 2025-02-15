package com.immortalidiot.roomintroduction.ui.di

import com.immortalidiot.roomintroduction.ui.user.UserScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::UserScreenViewModel)
}
