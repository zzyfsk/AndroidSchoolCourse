package com.zzy.b_koin.user

import androidx.lifecycle.ViewModel
import com.zzy.base.http.bean.UserDetailHttp

class UserOnlyKoinViewModel(private val userRes: UserRes): ViewModel(){
    val user = userRes.user
    val friendList = userRes.friendList

    fun isLogin():Boolean{
        return user.token.isNotEmpty()
    }

    fun devLogin()  {
        userRes.user = UserDetailHttp.testUser
    }

    fun getAccountName():String{
        return user.name.ifEmpty { userRes.getDeviceName() }
    }
}