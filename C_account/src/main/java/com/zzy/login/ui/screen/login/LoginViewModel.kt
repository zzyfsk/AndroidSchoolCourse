package com.zzy.login.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.b_koin.user.AccountHttpState

class LoginViewModel : ScreenModel {
    var pageState by mutableStateOf(StateLogin.Login)
    var httpState by mutableStateOf(AccountHttpState.None)

    var account by mutableStateOf("")
    var password by mutableStateOf("")
    var password2 by mutableStateOf("")

    val onHttpStateChange: (AccountHttpState) -> Unit = {
        httpState = it
    }

    val onAccountChange: (String) -> Unit = {
        account = it
    }

    val onPasswordChange: (String) -> Unit = {
        password = it
    }

    val onPassword2Change: (String) -> Unit = {
        password2 = it
    }

    fun checkPassword(): Boolean {
        return if (password != password2) {
            false
        } else if (password.isBlank()) {
            false
        } else {
            true
        }
    }

    fun stateReset() {
        httpState = AccountHttpState.None
    }
}

enum class StateLogin {
    Login,
    Register
}