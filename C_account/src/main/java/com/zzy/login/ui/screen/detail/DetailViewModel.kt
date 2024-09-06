package com.zzy.login.ui.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.base.http.bean.UserDetailHttp

class DetailViewModel : ScreenModel {
    var user by mutableStateOf(UserDetailHttp())

}