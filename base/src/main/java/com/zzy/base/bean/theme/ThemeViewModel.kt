package com.zzy.base.bean.theme

import androidx.lifecycle.ViewModel

class ThemeViewModel(private val themeRepository: ThemeRepository) : ViewModel() {
    fun getTheme(): Theme {
        return themeRepository.getTheme()
    }

    fun changeTheme(theme: Theme){
        themeRepository.changeTheme(theme)
    }

    fun changeTheme(){
        themeRepository.changeTheme()
    }
}