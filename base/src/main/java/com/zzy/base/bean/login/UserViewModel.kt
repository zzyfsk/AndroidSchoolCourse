package com.zzy.base.bean.login

import androidx.lifecycle.ViewModel
import com.zzy.base.util.HttpUtil

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun login(account: String, password: String) {
        val result = HttpUtil.instance.post<User>(
            url = "116.198.234.250/login",
            params = mapOf("account" to account, "password" to password)
        )
        if (result!=null&&result.token.isNotEmpty()){
            userRepository.loginUser(result.name,result.token)
        }
    }

    fun logout() {
        HttpUtil.instance.post<User>(
            url = "116.198.234.250/logout",
            params = mapOf()
        )
        userRepository.logOut()

    }
}