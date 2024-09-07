package com.zzy.login.ui.screen.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

class EditViewModel: ScreenModel {

    var account by mutableStateOf("")
    var password by mutableStateOf("")
    var password2 by mutableStateOf("")
    var name by mutableStateOf("")
    var signature by mutableStateOf("")

    val onAccountChange:(String)->Unit = {account = it}
    val onPasswordChange:(String)->Unit = {password = it}
    val onPassword2Change:(String)->Unit = {password2 = it}
    val onNameChange:(String)->Unit = {name = it}
    val onSignatureChange:(String)->Unit = {signature = it}

}