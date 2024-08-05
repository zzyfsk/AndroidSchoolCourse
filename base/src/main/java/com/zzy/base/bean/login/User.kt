package com.zzy.base.bean.login

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

data class User(val name: String, val token: String)

interface UserRepository {
    fun loginUser(name: String,token: String): User?
    fun logOut()
}

class UserRepositoryImpl : UserRepository {
    private var _user = User("", "")

    override fun loginUser(name: String, token: String): User {
        _user = User(name, token)
        return _user
    }

    override fun logOut() {
        _user = User("", "")
    }
}

val appModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    viewModel { UserViewModel(get()) }
}