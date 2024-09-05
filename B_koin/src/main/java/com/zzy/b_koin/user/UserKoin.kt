package com.zzy.b_koin.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.zzy.base.http.bean.Http
import com.zzy.base.http.bean.HttpInformation
import com.zzy.base.http.bean.UserDetailHttp
import com.zzy.base.util.HttpUtil
import exception.HttpNullException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.http.conn.ConnectTimeoutException
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

interface UserRes{
    fun login(account:String,password:String) : UserDetailHttp
    fun logout()
    fun getDeviceName():String
    fun getHttpState(): AccountHttpState
    fun setHttpState(state: AccountHttpState)
}

class UserKoin : UserRes{
    var state by mutableStateOf(AccountHttpState.None)
    private val deviceName = android.os.Build.DEVICE
    private val modelName = android.os.Build.MODEL

    override fun login(account: String, password: String): UserDetailHttp {
        var user:UserDetailHttp? = null
        var wrongInformation = ""
        CoroutineScope(Dispatchers.Default).launch {
            val result = HttpUtil.instance.post<UserDetailHttp>(
                url = "${Http.HTTP}/user/login",
                params = mapOf("name" to account, "password" to password)
            )
            println("result ${result.code}")
            if (result.code != "0") {
                when (result.code) {
                    "-1" -> {
                        wrongInformation = HttpInformation.`-1`
                    }

                    "101" -> {
                        wrongInformation = HttpInformation.`101`
                    }
                }
            } else {
                user = result.getData() as UserDetailHttp
            }
        }
        return user?:throw HttpNullException(wrongInformation)
    }

    override fun logout() {

    }

    override fun getDeviceName(): String {
        return "${deviceName}_$modelName"
    }

    override fun getHttpState(): AccountHttpState {
        return state
    }

    override fun setHttpState(state: AccountHttpState) {
        this.state = state
    }


}

val userModule = module {
    single<UserRes> { UserKoin() }
    viewModel { UserKoinViewModel(get()) }
}