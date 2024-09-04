package com.zzy.b_koin.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import exception.HttpNullException

class UserKoinViewModel(private val userRes: UserRes) : ViewModel() {

    var httpState by mutableStateOf(AccountHttpState.None)
    var wrongInformation by mutableStateOf("")

    fun login(account: String, password: String) {
        httpState = AccountHttpState.Connecting
        try {
            userRes.login(account, password)
//            httpState = AccountHttpState.Success
        } catch (e:HttpNullException){
            httpState = AccountHttpState.Fail
            wrongInformation = e.information
        }
//        catch (e:TimeoutCancellationException){
//            httpState = AccountHttpState.Wrong
//        }
    }

    fun getAccount():String{
       return userRes.getDeviceName()
    }

    fun register(account: String, password: String){
        // TODO 注册
    }

    fun stateReset() = run { httpState = AccountHttpState.None }

}

enum class AccountHttpState {
    None,
    Success,
    Connecting,
    Fail,
    Wrong
}