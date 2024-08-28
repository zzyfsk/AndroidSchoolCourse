package com.zzy.base.koin.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.zzy.base.http.bean.UserDetailHttp
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

data class User(val name: String,val id:Long, val token: String)

interface UserRepository {
    fun loginUser(name: String,id: Long, token: String,score:Int,signature:String): UserDetailHttp
    fun loginUser(userDetailHttp: UserDetailHttp): UserDetailHttp
    fun logOut()
    fun getUser(): UserDetailHttp
    fun addFriendAll(list: List<UserDetailHttp>)
    fun getFriendList(): List<UserDetailHttp>
    fun addFriend(friend: UserDetailHttp)
    fun removeAll()
}

class AccountRepositoryImpl : UserRepository {
    private var _user by mutableStateOf(UserDetailHttp())
    private var _friendList = mutableStateListOf<UserDetailHttp>()

    override fun loginUser(
        name: String,
        id: Long,
        token: String,
        score: Int,
        signature: String
    ): UserDetailHttp {
        _user = UserDetailHttp(id, name, score, signature,token)
        return _user
    }

    override fun loginUser(userDetailHttp: UserDetailHttp): UserDetailHttp {
        _user = userDetailHttp
        return  userDetailHttp
    }

    override fun logOut() {
        _user = UserDetailHttp()
    }

    override fun getUser(): UserDetailHttp {
        return _user
    }

    override fun addFriendAll(list: List<UserDetailHttp>) {
        _friendList.addAll(list)
    }

    override fun getFriendList(): List<UserDetailHttp> {
        return _friendList
    }

    override fun addFriend(friend: UserDetailHttp) {
        _friendList.add(friend)
    }

    override fun removeAll(){
        _friendList.clear()
    }
}

val appModule = module {
    single<UserRepository> { AccountRepositoryImpl() }
    viewModel { AccountViewModel(get()) }
}