package com.zzy.base.koin.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zzy.base.http.bean.Http
import com.zzy.base.util.HttpUtil
import com.zzy.base.http.bean.UserHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {
    val user: () -> User = { userRepository.getUser() }
    private val deviceName = android.os.Build.DEVICE
    private val modelName = android.os.Build.MODEL

    val friendList: () -> List<Friend> = { userRepository.getFriendList() }

    var httpState by mutableStateOf(LoginHttpState.None)

    fun isLogin(): Boolean {
        return user().token.isNotEmpty()
    }

    fun login(account: String, password: String) {
        httpState = LoginHttpState.Loading
        CoroutineScope(Dispatchers.Default).launch {
            val result = HttpUtil.instance.post<UserHttp>(
                url = "${Http.HTTP}/user/login",
                params = mapOf("name" to account, "password" to password)
            )
            println("result ${result.code}")
            if (result.code != "0") {
                when (result.code) {
                    "-1" -> {
                        // TODO 展示错误信息
                    }

                    "101" -> {

                    }
                }
                httpState = LoginHttpState.LoginFail
            } else {
                val user = result.getData() as UserHttp
                println(user.username)
                userRepository.loginUser(user.username, "1")
                httpState = LoginHttpState.LoginSuccess
            }
        }
    }

    fun register(account: String, password: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val result = HttpUtil.instance.post<UserHttp>(
                url = "${Http.HTTP}/user/register",
                params = mapOf("name" to account, "password" to password)
            )
            if (result.code != "0") {
                when (result.code) {
                    "-1" -> {
                        // TODO 展示错误信息
                    }
                }
            }else{
                login(account,password)
            }
            println("result ${result.code}")
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.Default).launch {
            HttpUtil.instance.post<UserHttp>(
                url = "116.198.234.250:10001/user/logout",
                params = mapOf()
            )
            userRepository.logOut()
        }
    }

    fun getFriendList() {
        userRepository.removeAll()
        CoroutineScope(Dispatchers.Default).launch {
            val result = HttpUtil.instance.post<UserHttp>(
                url = "${Http.HTTP}/friendList",
                params = mapOf("account" to user().name, "token" to user().token)
            ) // no server
            addFriendAll(
                listOf(
                    Friend("test User", "test"),
                    Friend("test User", "test"),
                    Friend("test User", "test")
                )
            )
        }
    }

    private fun addFriendAll(list: List<Friend>) {
        userRepository.addFriendAll(list)
    }

    fun getAccount(): String {
        return "${deviceName}_$modelName"
    }
}

enum class LoginHttpState{
    None,
    Loading,
    LoginSuccess,
    LoginFail,
    RegisterSuccess,
    RegisterFai
}