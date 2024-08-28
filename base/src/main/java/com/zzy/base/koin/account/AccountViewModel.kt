package com.zzy.base.koin.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zzy.base.http.bean.Http
import com.zzy.base.http.bean.UserDetailHttp
import com.zzy.base.http.bean.UserHttp
import com.zzy.base.util.HttpUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {
    val user: () -> UserDetailHttp = { userRepository.getUser() }
    private val deviceName = android.os.Build.DEVICE
    private val modelName = android.os.Build.MODEL

    val friendList: () -> List<UserDetailHttp> = { userRepository.getFriendList() }

    var httpState by mutableStateOf(LoginHttpState.None)

    fun isLogin(): Boolean {
        return user().token.isNotEmpty()
    }

    fun login(account: String, password: String) {
        httpState = LoginHttpState.Loading
        CoroutineScope(Dispatchers.Default).launch {
            val result = HttpUtil.instance.post<UserDetailHttp>(
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
                val user = result.getData() as UserDetailHttp
                println(user.name)
                userRepository.loginUser(id = user.id, name = user.name, score = user.score, signature = user.signature, token = "-1")
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

                    "102" -> {
                        httpState = LoginHttpState.RegisterFail
                    }
                }
            } else {
                login(account, password)
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
            val result = HttpUtil.instance.post<List<UserDetailHttp>>(
                url = "${Http.HTTP}/user/getFriends",
                params = mapOf("id" to user().id.toString())
            )
            if (result.code != "0") {
                addFriendAll(
                    listOf(
                        UserDetailHttp(1, "test User"),
                        UserDetailHttp(2, "test User"),
                        UserDetailHttp(3, "test User")
                    )
                )
            } else {
                println(result.getData())
                result.getData()?.let { addFriendAll(it) }
            }
        }
    }

    private fun addFriendAll(list: List<UserDetailHttp>) {
        userRepository.addFriendAll(list)
    }

    fun getAccount(): String {
        return "${deviceName}_$modelName"
    }

    fun devAccount(){
        userRepository.loginUser(
            UserDetailHttp.testUser
        )
    }
}

enum class LoginHttpState {
    None,
    Loading,
    LoginSuccess,
    LoginFail,
    RegisterSuccess,
    RegisterFail
}