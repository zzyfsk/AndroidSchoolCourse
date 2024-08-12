package com.zzy.login.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.base.koin.account.LoginHttpState

class LoginViewModel: ScreenModel {
    var state by mutableStateOf(StateLogin.Login)
    var httpState by mutableStateOf(LoginHttpState.None)

    var account by mutableStateOf("")
    var password by mutableStateOf("")
    var password2 by mutableStateOf("")

    val onAccountChange: (String) -> Unit = {
        account = it
    }

    val onPasswordChange: (String) -> Unit = {
        password = it
    }

    val onPassword2Change: (String) -> Unit = {
        password2 = it
    }

    fun checkPassword():Boolean{
        return if (password != password2){
            false
        }else if (password.isBlank()){
            false
        } else{
            true
        }
    }
}

enum class StateLogin{
    Login,
    Register
}