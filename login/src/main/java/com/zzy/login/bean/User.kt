package com.zzy.login.bean

import org.koin.dsl.module

data class User(val name: String)

interface UserRepository {
    fun findUser(name: String):User?
    fun addUser(user: User)
}

class UserRepositoryImpl : UserRepository{
    private val _user = arrayListOf<User>()

    override fun findUser(name: String): User? {
        return _user.firstOrNull{it.name == name}
    }

    override fun addUser(user: User) {
        _user.add(user)
    }

}

val appModule = module {
    single<UserRepository> { UserRepositoryImpl() }

}