package com.zzy.base.bean.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

data class User(val name: String, val token: String)

interface UserRepository {
    fun loginUser(name: String, token: String): User?
    fun logOut()
    fun getUser(): User
}

class AccountRepositoryImpl : UserRepository {
    private var _user by mutableStateOf(User("", ""))

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
}

val appModule = module {
    single<UserRepository> { AccountRepositoryImpl() }
    viewModel { AccountViewModel(get()) }
}