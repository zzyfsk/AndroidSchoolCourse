package com.zzy.b_koin.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.zzy.base.http.bean.Http
import com.zzy.base.http.bean.HttpInformation
import com.zzy.base.http.bean.UserDetailHttp
import com.zzy.base.util.HttpUtil
import exception.HttpNullException
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

interface UserRes {
    var user: UserDetailHttp
    val friendList: SnapshotStateList<UserDetailHttp>
    suspend fun login(account: String, password: String): UserDetailHttp
    suspend fun register(account: String, password: String): UserDetailHttp
    suspend fun update(account: String, password: String, name: String, signature: String) :UserDetailHttp
    suspend fun getFriends(id: Long): List<UserDetailHttp>
    suspend fun getFriend(id: Long): UserDetailHttp
    fun logout()
    fun getDeviceName(): String
    fun getHttpState(): AccountHttpState
    fun setHttpState(state: AccountHttpState)
}

class UserKoin : UserRes {
    var state by mutableStateOf(AccountHttpState.None)
    private val deviceName = android.os.Build.DEVICE
    private val modelName = android.os.Build.MODEL

    override val friendList = mutableStateListOf<UserDetailHttp>()
    override var user: UserDetailHttp by mutableStateOf(UserDetailHttp())

    override suspend fun login(account: String, password: String): UserDetailHttp {
        var user: UserDetailHttp? = null
        var wrongInformation = ""
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
            user = result.getData()?.copy(token = "xx")
        }
        return user ?: throw HttpNullException(wrongInformation)
    }

    override suspend fun register(account: String, password: String): UserDetailHttp {
        var user: UserDetailHttp? = null
        var wrongInformation = ""

        val result = HttpUtil.instance.post<UserDetailHttp>(
            url = "${Http.HTTP}/user/register",
            params = mapOf("name" to account, "password" to password)
        )
        if (result.code != "0") {
            when (result.code) {
                "-1" -> {
                    wrongInformation = HttpInformation.`-1`
                }
            }
        } else {
            user = result.getData()
        }
        return user ?: throw HttpNullException(wrongInformation)
    }

    override suspend fun update(
        account: String,
        password: String,
        name: String,
        signature: String
    ): UserDetailHttp {
        var wrongInformation = ""
        var user: UserDetailHttp? = null

        val result = HttpUtil.instance.post<UserDetailHttp>(
            url = "${Http.HTTP}/user/update",
            params = mapOf("account" to account, "password" to password, "name" to name, "signature" to signature)
        )
        if (result.code!= "0") {
            when (result.code) {
                "-1" -> {
                    wrongInformation = HttpInformation.`-1`
                }
            }
        }else{
            user = result.getData()
            this@UserKoin.user = user?: throw RuntimeException("Unknown Error")
        }
        return user ?: throw HttpNullException(wrongInformation)
    }

    override suspend fun getFriends(id: Long): List<UserDetailHttp> {
        friendList.clear()
        val result = HttpUtil.instance.post<List<UserDetailHttp>>(
            url = "${Http.HTTP}/user/getFriends",
            params = mapOf("id" to id.toString())
        )
        if (result.code != "0") {
            friendList.addAll(
                listOf(
                    UserDetailHttp(1, "test User"),
                    UserDetailHttp(2, "test User"),
                    UserDetailHttp(3, "test User")
                )
            )
        } else {
            result.getData()?.let { friendList.addAll(it) }
        }

        return friendList
    }

    override suspend fun getFriend(id: Long): UserDetailHttp {
        val userDetailHttp: UserDetailHttp?
        val result = HttpUtil.instance.post<UserDetailHttp>(
            url = "${Http.HTTP}/user/getFriend",
            params = mapOf("id" to id.toString())
        )
        if (result.code != "0") {
            when (result.code) {
                "-1" -> {

                }
            }
            userDetailHttp = null
        } else {
            userDetailHttp = result.getData()
        }

        return userDetailHttp ?: throw HttpNullException("null")
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
    viewModel {
        UserKoinViewModel(get())
    }
    viewModel {
        UserOnlyKoinViewModel(get())
    }
}