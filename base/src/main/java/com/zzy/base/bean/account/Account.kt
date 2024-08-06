package com.zzy.base.bean.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

data class User(val name: String, val token: String)
data class Friend(val name: String, val signature:String)

interface UserRepository {
    fun loginUser(name: String, token: String): User?
    fun logOut()
    fun getUser(): User
    fun addFriendAll(list: List<Friend>)
    fun getFriendList(): List<Friend>
    fun addFriend(friend: Friend)
    fun removeAll()
}

class AccountRepositoryImpl : UserRepository {
    private var _user by mutableStateOf(User("", ""))
    private var _friendList = mutableStateListOf<Friend>()

    override fun loginUser(name: String, token: String): User {
        _user = User(name, token)
        return _user
    }

    override fun logOut() {
        _user = User("", "")
    }

    override fun getUser(): User {
        return _user
    }

    override fun addFriendAll(list: List<Friend>) {
        _friendList.addAll(list)
    }

    override fun getFriendList(): List<Friend> {
        return _friendList
    }

    override fun addFriend(friend: Friend) {
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