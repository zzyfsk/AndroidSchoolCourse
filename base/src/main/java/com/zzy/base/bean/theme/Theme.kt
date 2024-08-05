package com.zzy.base.bean.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

data class ThemeBean(val theme:Theme)

enum class Theme{
    Normal,
    Dark
}

interface ThemeRepository{
    fun changeTheme()
    fun changeTheme(theme: Theme)
    fun getTheme():Theme
}

class ThemeRepositoryImpl:ThemeRepository{
    private var _theme by mutableStateOf(Theme.Normal)
    override fun changeTheme() {
        _theme = if (_theme == Theme.Normal) Theme.Dark else Theme.Normal
    }

    override fun changeTheme(theme: Theme) {
        _theme = theme
    }

    override fun getTheme() = _theme
}

val themeModule = module {
    single<ThemeRepository> { ThemeRepositoryImpl() }
    viewModel { ThemeViewModel(get()) }
}