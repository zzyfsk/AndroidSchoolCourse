package com.zzy.base.bean.account

import androidx.lifecycle.ViewModel
import com.zzy.base.util.HttpUtil

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {
    val user:()->User = { userRepository.getUser() }

    fun isLogin():Boolean{
        return user().token.isNotEmpty()
    }

    fun login(account: String, password: String):Boolean {
        val result = HttpUtil.instance.post<User>(
            url = "116.198.234.250/login",
            params = mapOf("account" to account, "password" to password)
        )
        if (result!=null&&result.token.isNotEmpty()){
            userRepository.loginUser(result.name,result.token)
        }
        userRepository.loginUser("zzy","0")
        return true
    }

    fun logout() {
        HttpUtil.instance.post<User>(
            url = "116.198.234.250/logout",
            params = mapOf()
        )
        userRepository.logOut()

    }
}