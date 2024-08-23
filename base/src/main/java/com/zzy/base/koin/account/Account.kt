package com.zzy.base.koin.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.zzy.base.http.bean.UserDetailHttp
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

data class User(val name: String,val id:Long, val token: String)
data class Friend(val name: String, val signature:String)

interface UserRepository {
    fun loginUser(name: String,id: Long, token: String): User?
    fun logOut()
    fun getUser(): User
    fun addFriendAll(list: List<UserDetailHttp>)
    fun getFriendList(): List<UserDetailHttp>
    fun addFriend(friend: UserDetailHttp)
    fun removeAll()
}

class AccountRepositoryImpl : UserRepository {
    private var _user by mutableStateOf(User("", 0,""))
    private var _friendList = mutableStateListOf<UserDetailHttp>()

    override fun loginUser(name: String,id:Long, token: String): User {
        _user = User(name,id, token)
        return _user
    }

    override fun logOut() {
        _user = User("", 0,"")
    }

    override fun getUser(): User {
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