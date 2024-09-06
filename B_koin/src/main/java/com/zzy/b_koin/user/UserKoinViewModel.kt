package com.zzy.b_koin.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zzy.base.http.bean.UserDetailHttp
import exception.HttpNullException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserKoinViewModel(private val userRes: UserRes) : ViewModel() {
    var wrongInformation by mutableStateOf("")

    fun login(account: String, password: String,httpStateChange:(AccountHttpState)->Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            httpStateChange(AccountHttpState.Connecting)
            try {
                wrongInformation = ""
                userRes.user = userRes.login(account, password)
                httpStateChange(AccountHttpState.Success)
            } catch (e:HttpNullException){
                httpStateChange(AccountHttpState.Fail)
                wrongInformation = e.information
                println(wrongInformation)
            }
        }
    }



    fun register(account: String, password: String){
        // TODO 注册
    }

    fun stateReset() = userRes.setHttpState(AccountHttpState.None)

    fun setState(state: AccountHttpState) = userRes.setHttpState(state)

    fun getState() = userRes.getHttpState()
}

class UserOnlyKoinViewModel(private val userRes: UserRes):ViewModel(){
    val user = userRes.user
    val friendList = userRes.friendList

    fun isLogin():Boolean{
        return user.token.isNotEmpty()
    }

    fun devLogin()  {
        userRes.user = UserDetailHttp.testUser
    }

    fun getAccount():String{
        return userRes.getDeviceName()
    }
}

enum class AccountHttpState {
    None,
    Success,
    Connecting,
    Fail,
    Wrong
}