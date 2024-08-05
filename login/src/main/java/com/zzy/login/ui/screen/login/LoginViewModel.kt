package com.zzy.login.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.base.bean.account.AccountViewModel

class LoginViewModel: ScreenModel {

    var account by mutableStateOf("")
    var password by mutableStateOf("")

    val onAccountChange: (String) -> Unit = {
        account = it
    }

    val onPasswordChange: (String) -> Unit = {
        password = it
    }
}